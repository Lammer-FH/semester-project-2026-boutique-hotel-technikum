import { defineStore } from "pinia";
import type { PaginationMeta } from "../../core/models/api";
import type { Room } from "../../core/models/room";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../../core/constants";
import {
  normalizePageNumber,
  normalizePageSize,
  resolvePaginationRequest,
  shouldSkipPageChange,
  toPaginationCacheKey,
} from "../../core/pagination";
import { listRooms } from "../../infrastructure/api/roomApi";
import { toErrorMessage } from "./storeErrors";

const ROOM_CACHE_TTL_MS = 2 * 60 * 1000;

interface RoomCacheEntry {
  rooms: Room[];
  pagination: PaginationMeta;
  fetchedAt: number;
}

interface RoomState {
  rooms: Room[];
  pagination: PaginationMeta | null;
  isLoading: boolean;
  error: string | null;
  currentPage: number;
  pageSize: number;
  lastQuery: { page: number; size: number } | null;
  cache: Record<string, RoomCacheEntry>;
  latestRequestId: number;
}

export const useRoomStore = defineStore("rooms", {
  state: (): RoomState => ({
    rooms: [],
    pagination: null,
    isLoading: false,
    error: null,
    currentPage: DEFAULT_PAGE,
    pageSize: DEFAULT_PAGE_SIZE,
    lastQuery: null,
    cache: {},
    latestRequestId: 0,
  }),
  actions: {
    isCacheFresh(cacheKey: string) {
      const cached = this.cache[cacheKey];
      if (!cached) {
        return false;
      }

      return Date.now() - cached.fetchedAt < ROOM_CACHE_TTL_MS;
    },
    async fetchRooms(options?: { page?: number; size?: number; force?: boolean }) {
      const { page: requestedPage, size: requestedSize } = resolvePaginationRequest(
        options,
        {
          page: this.currentPage,
          size: this.pageSize,
        }
      );
      const cacheKey = toPaginationCacheKey(requestedPage, requestedSize);
      const cached = this.cache[cacheKey];

      if (!options?.force && cached && this.isCacheFresh(cacheKey)) {
        this.rooms = cached.rooms;
        this.pagination = cached.pagination;
        this.currentPage = requestedPage;
        this.pageSize = requestedSize;
        this.lastQuery = { page: requestedPage, size: requestedSize };
        this.error = null;
        return;
      }

      const requestId = ++this.latestRequestId;
      this.isLoading = true;
      this.error = null;

      try {
        const response = await listRooms(requestedPage, requestedSize);
        if (requestId !== this.latestRequestId) {
          return;
        }
        this.rooms = response.data;
        this.pagination = response.pagination;
        this.currentPage = requestedPage;
        this.pageSize = requestedSize;
        this.lastQuery = { page: requestedPage, size: requestedSize };
        this.cache[cacheKey] = {
          rooms: response.data,
          pagination: response.pagination,
          fetchedAt: Date.now(),
        };
      } catch (error) {
        if (requestId !== this.latestRequestId) {
          return;
        }
        this.error = toErrorMessage(error);
      } finally {
        if (requestId === this.latestRequestId) {
          this.isLoading = false;
        }
      }
    },
    async prefetchRooms(page: number, size: number) {
      const safePage = normalizePageNumber(page, this.currentPage);
      const safeSize = normalizePageSize(size, this.pageSize);
      const cacheKey = toPaginationCacheKey(safePage, safeSize);
      if (this.isCacheFresh(cacheKey)) {
        return;
      }

      try {
        const response = await listRooms(safePage, safeSize);
        this.cache[cacheKey] = {
          rooms: response.data,
          pagination: response.pagination,
          fetchedAt: Date.now(),
        };
      } catch {
        // Prefetch failures are non-blocking.
      }
    },
    setPage(page: number) {
      const nextPage = normalizePageNumber(page, this.currentPage);
      if (shouldSkipPageChange(nextPage, this.currentPage, !!this.error)) {
        return;
      }

      this.currentPage = nextPage;
      void this.fetchRooms({
        page: nextPage,
        size: this.pageSize,
        force: !!this.error,
      });
    },
    setPageSize(size: number) {
      const nextSize = normalizePageSize(size, this.pageSize);
      if (nextSize === this.pageSize) {
        return;
      }

      this.pageSize = nextSize;
      this.currentPage = DEFAULT_PAGE;
      void this.fetchRooms({ page: this.currentPage, size: nextSize, force: true });
    },
  },
});
