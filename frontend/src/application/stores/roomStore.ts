import { defineStore } from "pinia";
import type { PaginationMeta } from "../../core/models/api";
import type { Room } from "../../core/models/room";
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "../../core/constants";
import { getRoom, listRooms } from "../../infrastructure/api/roomApi";
import { toErrorMessage } from "./storeErrors";

interface RoomState {
  rooms: Room[];
  pagination: PaginationMeta | null;
  selectedRoom: Room | null;
  isLoading: boolean;
  error: string | null;
}

export const useRoomStore = defineStore("rooms", {
  state: (): RoomState => ({
    rooms: [],
    pagination: null,
    selectedRoom: null,
    isLoading: false,
    error: null,
  }),
  actions: {
    async fetchRooms(page = DEFAULT_PAGE, size = DEFAULT_PAGE_SIZE) {
      this.isLoading = true;
      this.error = null;

      try {
        const response = await listRooms(page, size);
        this.rooms = response.data;
        this.pagination = response.pagination;
      } catch (error) {
        this.error = toErrorMessage(error);
      } finally {
        this.isLoading = false;
      }
    },
    async fetchRoom(roomId: number) {
      this.isLoading = true;
      this.error = null;

      try {
        this.selectedRoom = await getRoom(roomId);
      } catch (error) {
        this.error = toErrorMessage(error);
      } finally {
        this.isLoading = false;
      }
    },
    clearSelectedRoom() {
      this.selectedRoom = null;
    },
  },
});
