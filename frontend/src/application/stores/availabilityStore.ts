import { defineStore } from "pinia";
import type { AvailabilityResult } from "../../core/models/availability";
import { checkAvailability } from "../../infrastructure/api/availabilityApi";
import { toErrorMessage } from "../../core/storeErrors";
import { logger } from "../../core/logger";

interface AvailabilityState {
  availabilityByRoomId: Record<number, AvailabilityResult>;
  lastRequestByRoomId: Record<number, string>;
  controllersByRoomId: Record<number, AbortController>;
  loadingByRoomId: Record<number, boolean>;
  error: string | null;
}

export const useAvailabilityStore = defineStore("availability", {
  state: (): AvailabilityState => ({
    availabilityByRoomId: {},
    lastRequestByRoomId: {},
    controllersByRoomId: {},
    loadingByRoomId: {},
    error: null,
  }),
  getters: {
    isLoading: (state): boolean =>
      Object.values(state.loadingByRoomId).some(Boolean),
  },
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
      this.loadingByRoomId[roomId] = true;
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
          this.loadingByRoomId[roomId] = false;
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
        this.loadingByRoomId = {};
        return;
      }

      this.controllersByRoomId[roomId]?.abort();
      delete this.controllersByRoomId[roomId];
      delete this.availabilityByRoomId[roomId];
      delete this.lastRequestByRoomId[roomId];
      delete this.loadingByRoomId[roomId];
    },
    resetAvailabilityFlow(roomId?: number) {
      this.error = null;
      if (roomId !== undefined) {
        this.loadingByRoomId[roomId] = false;
      } else {
        this.loadingByRoomId = {};
      }
      this.clearAvailability(roomId);
    },
  },
});
