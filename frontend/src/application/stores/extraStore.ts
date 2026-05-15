import { defineStore } from "pinia";
import type { Extra } from "../../core/models/room";
import { listExtras } from "../../infrastructure/api/extraApi";
import { toErrorMessage } from "./storeErrors";

interface ExtraState {
  extras: Extra[];
  isLoading: boolean;
  error: string | null;
}

export const useExtraStore = defineStore("extras", {
  state: (): ExtraState => ({
    extras: [],
    isLoading: false,
    error: null,
  }),
  actions: {
    async fetchExtras() {
      this.isLoading = true;
      this.error = null;

      try {
        this.extras = await listExtras();
      } catch (error) {
        this.error = toErrorMessage(error);
      } finally {
        this.isLoading = false;
      }
    },
  },
});
