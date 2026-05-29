import { defineStore } from "pinia";
import type { AvailabilityResult } from "../../core/models/availability";
import { checkAvailability } from "../../infrastructure/api/availabilityApi";
import { toErrorMessage } from "../../core/storeErrors";
import { logger } from "../../core/logger";

interface AvailabilityState {
  availabilityByRoomId: Record<number, AvailabilityResult>;
  lastRequestByRoomId: Record<number, string>;
  controllersByRoomId: Record<number, AbortController>;
  isLoading: boolean;
  error: string | null;
}

export const useAvailabilityStore = defineStore("availability", {
  state: (): AvailabilityState => ({
    availabilityByRoomId: {},
    lastRequestByRoomId: {},
    controllersByRoomId: {},
    isLoading: false,
    error: null,
  }),
  actions: {
    async checkRoomAvailability(
      roomId: number,
      checkInDate: string,
      checkOutDate: string
    ) {
      const requestKey = `${checkInDate}|${checkOutDate}`;
      if (this.lastRequestByRoomId[roomId] === requestKey && !this.error) {
        return this.availabilityByRoomId[roomId] ?? null;
      }

      if (this.controllersByRoomId[roomId]) {
        this.controllersByRoomId[roomId].abort();
      }

      const controller = new AbortController();
      this.controllersByRoomId[roomId] = controller;
      this.lastRequestByRoomId[roomId] = requestKey;
      this.isLoading = true;
      this.error = null;

      try {
        const result = await checkAvailability(
          roomId,
          checkInDate,
          checkOutDate,
          controller.signal
        );
        this.availabilityByRoomId[roomId] = result;
        return result;
      } catch (error) {
        if (controller.signal.aborted) {
          return null;
        }
        this.error = toErrorMessage(error);
        logger.error("availability", "Availability check failed", {
          roomId,
          checkInDate,
          checkOutDate,
          error: this.error,
        });
        return null;
      } finally {
        delete this.controllersByRoomId[roomId];
        if (!controller.signal.aborted) {
          this.isLoading = false;
        }
      }
    },
    clearAvailability(roomId?: number) {
      if (roomId === undefined) {
        for (const controller of Object.values(this.controllersByRoomId)) {
          controller.abort();
        }
        this.controllersByRoomId = {};
        this.availabilityByRoomId = {};
        this.lastRequestByRoomId = {};
        return;
      }

      this.controllersByRoomId[roomId]?.abort();
      delete this.controllersByRoomId[roomId];
      delete this.availabilityByRoomId[roomId];
      delete this.lastRequestByRoomId[roomId];
    },
    resetAvailabilityFlow(roomId?: number) {
      this.error = null;
      this.isLoading = false;
      this.clearAvailability(roomId);
    },
  },
});
