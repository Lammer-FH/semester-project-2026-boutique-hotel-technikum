import { defineStore } from "pinia";
import type {
  BookingConfirmation,
  BookingRequest,
} from "../../core/models/booking";
import { createBooking, getBooking } from "../../infrastructure/api/bookingApi";
import { toErrorMessage } from "./storeErrors";

interface BookingState {
  currentBooking: BookingConfirmation | null;
  draft: Partial<BookingRequest>;
  isSubmitting: boolean;
  error: string | null;
}

export const useBookingStore = defineStore("booking", {
  state: (): BookingState => ({
    currentBooking: null,
    draft: {},
    isSubmitting: false,
    error: null,
  }),
  actions: {
    updateDraft(patch: Partial<BookingRequest>) {
      this.draft = { ...this.draft, ...patch };
    },
    clearDraft() {
      this.draft = {};
    },
    async submitBooking(request: BookingRequest) {
      this.isSubmitting = true;
      this.error = null;

      try {
        this.currentBooking = await createBooking(request);
        return this.currentBooking;
      } catch (error) {
        this.error = toErrorMessage(error);
        return null;
      } finally {
        this.isSubmitting = false;
      }
    },
    async fetchBooking(bookingId: number) {
      this.isSubmitting = true;
      this.error = null;

      try {
        this.currentBooking = await getBooking(bookingId);
        return this.currentBooking;
      } catch (error) {
        this.error = toErrorMessage(error);
        return null;
      } finally {
        this.isSubmitting = false;
      }
    },
    clearBooking() {
      this.currentBooking = null;
    },
  },
});
