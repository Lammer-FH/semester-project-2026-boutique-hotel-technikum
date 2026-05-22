import { defineStore } from "pinia";
import type { AvailabilityResult } from "../../core/models/availability";
import { checkAvailability } from "../../infrastructure/api/availabilityApi";
import { toErrorMessage } from "./storeErrors";

interface AvailabilityState {
  availabilityByRoomId: Record<number, AvailabilityResult>;
  isLoading: boolean;
  error: string | null;
}

export const useAvailabilityStore = defineStore("availability", {
  state: (): AvailabilityState => ({
    availabilityByRoomId: {},
    isLoading: false,
    error: null,
  }),
  actions: {
    async checkRoomAvailability(
      roomId: number,
      checkInDate: string,
      checkOutDate: string
    ) {
      this.isLoading = true;
      this.error = null;

      try {
        const result = await checkAvailability(
          roomId,
          checkInDate,
          checkOutDate
        );
        this.availabilityByRoomId[roomId] = result;
        return result;
      } catch (error) {
        this.error = toErrorMessage(error);
        return null;
      } finally {
        this.isLoading = false;
      }
    },
    clearAvailability(roomId?: number) {
      if (roomId === undefined) {
        this.availabilityByRoomId = {};
        return;
      }

      delete this.availabilityByRoomId[roomId];
    },
  },
});
