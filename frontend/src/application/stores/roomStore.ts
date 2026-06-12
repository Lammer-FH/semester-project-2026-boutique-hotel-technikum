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
import { toErrorMessage } from "../../core/storeErrors";

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
  checkInDate: string | null;
  checkOutDate: string | null;
  lastQuery: { page: number; size: number } | null;
  cache: Record<string, RoomCacheEntry>;
  fetchController: AbortController | null;
}

const buildCacheKey = (
  page: number,
  size: number,
  checkInDate: string | null,
  checkOutDate: string | null
): string =>
  `${toPaginationCacheKey(page, size)}|${checkInDate ?? ""}|${checkOutDate ?? ""}`;

export const useRoomStore = defineStore("rooms", {
  state: (): RoomState => ({
    rooms: [],
    pagination: null,
    isLoading: false,
    error: null,
    currentPage: DEFAULT_PAGE,
    pageSize: DEFAULT_PAGE_SIZE,
    checkInDate: null,
    checkOutDate: null,
    lastQuery: null,
    cache: {},
    fetchController: null,
  }),
  getters: {
    hasRooms: (state): boolean => state.rooms.length > 0,
    totalPages: (state): number => state.pagination?.totalPages ?? 1,
    totalRooms: (state): number => state.pagination?.total ?? state.rooms.length,
    isReady: (state): boolean => !state.isLoading,
    hasDateFilter: (state): boolean =>
      Boolean(state.checkInDate && state.checkOutDate),
  },
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
      const cacheKey = buildCacheKey(
        requestedPage,
        requestedSize,
        this.checkInDate,
        this.checkOutDate
      );
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

      this.fetchController?.abort();
      const controller = new AbortController();
      this.fetchController = controller;
      this.isLoading = true;
      this.error = null;

      try {
        const response = await listRooms(requestedPage, requestedSize, controller.signal, {
          checkInDate: this.checkInDate ?? undefined,
          checkOutDate: this.checkOutDate ?? undefined,
        });
        if (controller.signal.aborted) {
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
        if (controller.signal.aborted) {
          return;
        }
        this.error = toErrorMessage(error);
      } finally {
        this.fetchController = null;
        if (!controller.signal.aborted) {
          this.isLoading = false;
        }
      }
    },
    async prefetchRooms(page: number, size: number) {
      const safePage = normalizePageNumber(page, this.currentPage);
      const safeSize = normalizePageSize(size, this.pageSize);
      const cacheKey = buildCacheKey(
        safePage,
        safeSize,
        this.checkInDate,
        this.checkOutDate
      );
      if (this.isCacheFresh(cacheKey)) {
        return;
      }

      try {
        const response = await listRooms(safePage, safeSize, undefined, {
          checkInDate: this.checkInDate ?? undefined,
          checkOutDate: this.checkOutDate ?? undefined,
        });
        this.cache[cacheKey] = {
          rooms: response.data,
          pagination: response.pagination,
          fetchedAt: Date.now(),
        };
      } catch {
        // Prefetch failures are non-blocking.
      }
    },
    applyDateFilter(checkInDate: string, checkOutDate: string) {
      if (
        this.checkInDate === checkInDate &&
        this.checkOutDate === checkOutDate
      ) {
        return;
      }

      this.checkInDate = checkInDate;
      this.checkOutDate = checkOutDate;
      this.currentPage = DEFAULT_PAGE;
      void this.fetchRooms({ page: DEFAULT_PAGE, size: this.pageSize, force: true });
    },
    clearDateFilter() {
      if (!this.checkInDate && !this.checkOutDate) {
        return;
      }

      this.checkInDate = null;
      this.checkOutDate = null;
      this.currentPage = DEFAULT_PAGE;
      void this.fetchRooms({ page: DEFAULT_PAGE, size: this.pageSize, force: true });
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
