import { defineStore } from "pinia";
import type {
  BookingConfirmation,
  BookingRequest,
} from "../../core/models/booking";
import { validateDateRange } from "../../core/dateutils";
import { createBooking, getBooking } from "../../infrastructure/api/bookingApi";
import { toErrorMessage } from "../../core/storeErrors";
import { logger } from "../../core/logger";

interface BookingState {
  currentBooking: BookingConfirmation | null;
  draft: Partial<BookingRequest>;
  isLoading: boolean;
  isSubmitting: boolean;
  error: string | null;
}

export const useBookingStore = defineStore("booking", {
  state: (): BookingState => ({
    currentBooking: null,
    draft: {},
    isLoading: false,
    isSubmitting: false,
    error: null,
  }),
  actions: {
    validateRequest(request: BookingRequest) {
      if (!request.roomId || request.roomId < 1) {
        return "Room selection is required.";
      }

      const dateError = validateDateRange(
        request.checkInDate,
        request.checkOutDate
      );
      if (dateError) {
        return dateError;
      }

      if (!request.guestFirstName?.trim() || !request.guestLastName?.trim()) {
        return "Guest name is required.";
      }

      if (!request.guestEmail?.trim() || !request.confirmEmail?.trim()) {
        return "Email address is required.";
      }

      if (request.guestEmail.trim() !== request.confirmEmail.trim()) {
        return "Email addresses must match.";
      }

      if (!Number.isFinite(request.guestCount) || request.guestCount < 1) {
        return "Guest count must be at least 1.";
      }

      return null;
    },
    resetBookingFlow() {
      this.currentBooking = null;
      this.draft = {};
      this.error = null;
      this.isSubmitting = false;
      this.isLoading = false;
    },
    updateDraft(patch: Partial<BookingRequest>) {
      this.draft = { ...this.draft, ...patch };
    },
    clearDraft() {
      this.draft = {};
    },
    async submitBooking(request: BookingRequest) {
      if (this.isSubmitting) {
        return null;
      }

      const validation = this.validateRequest(request);
      if (validation) {
        this.error = validation;
        return null;
      }

      this.isSubmitting = true;
      this.error = null;

      try {
        this.currentBooking = await createBooking(request);
        logger.info("booking", "Booking created", {
          bookingId: this.currentBooking.bookingId,
          roomId: request.roomId,
        });
        return this.currentBooking;
      } catch (error) {
        this.error = toErrorMessage(error);
        logger.error("booking", "Booking creation failed", {
          roomId: request.roomId,
          error: this.error,
        });
        return null;
      } finally {
        this.isSubmitting = false;
      }
    },
    async getBooking(bookingId: number) {
      this.isLoading = true;
      this.error = null;

      try {
        this.currentBooking = await getBooking(bookingId);
        return this.currentBooking;
      } catch (error) {
        this.error = toErrorMessage(error);
        logger.error("booking", "Booking fetch failed", {
          bookingId,
          error: this.error,
        });
        return null;
      } finally {
        this.isLoading = false;
      }
    },
    clearBooking() {
      this.currentBooking = null;
    },
  },
});
