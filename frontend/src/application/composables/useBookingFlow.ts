import { computed, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useBookingStore } from "@/application/stores/bookingStore";
import { formatDate } from "@/core/dateutils";
import { bookingDialogContent } from "@/data/content/bookingContent";
import {
  buildBookingRequest,
  type BookingRequest,
} from "@/core/models/booking";

export interface UseBookingFlowOptions {
  isOpen: () => boolean;
  roomId: () => number;
  roomMaxGuests: () => number;
  checkInDate: () => string;
  checkOutDate: () => string;
  onClose: () => void;
  onCompleted: () => void;
  onChangeDates: () => void;
}

export function useBookingFlow(options: UseBookingFlowOptions) {
  const bookingStore = useBookingStore();
  const { draft, isSubmitting, error } = storeToRefs(bookingStore);
  const router = useRouter();

  const bookingErrorMessage = ref("");
  const bookingStep = ref<"details" | "review">("details");

  const dateRangeLabel = computed(() =>
    options.checkInDate() && options.checkOutDate()
      ? `${formatDate(options.checkInDate())} - ${formatDate(options.checkOutDate())}`
      : ""
  );

  const guestCountOptions = computed(() =>
    Array.from({ length: options.roomMaxGuests() }, (_, index) => index + 1)
  );

  const bookingMessage = computed(
    () => bookingErrorMessage.value || error.value || ""
  );

  const stepContent = computed(() => {
    const key = bookingStep.value;
    return {
      label: bookingDialogContent.stepLabel[key],
      title: bookingDialogContent.stepTitle[key],
      hint: bookingDialogContent.hints[key],
    };
  });

  const clearBookingFeedback = () => {
    bookingErrorMessage.value = "";
    bookingStore.clearBooking();
  };

  const closeDialog = () => {
    bookingStep.value = "details";
    bookingStore.resetBookingFlow();
    options.onClose();
  };

  const setDraftValue = <K extends keyof BookingRequest>(
    key: K,
    value: BookingRequest[K]
  ) => {
    bookingStore.updateDraft({ [key]: value } as Partial<BookingRequest>);
  };

  const buildRequest = (): BookingRequest =>
    buildBookingRequest(draft.value, {
      roomId: options.roomId(),
      checkInDate: options.checkInDate(),
      checkOutDate: options.checkOutDate(),
    });

  const validate = () =>
    bookingStore.validateRequest(buildRequest(), {
      maxGuests: options.roomMaxGuests(),
    });

  const proceedToReview = () => {
    clearBookingFeedback();
    const validation = validate();
    if (validation) {
      bookingErrorMessage.value = validation;
      return;
    }

    bookingStep.value = "review";
  };

  const backToDetails = () => {
    bookingStep.value = "details";
  };

  const requestChangeDates = () => {
    options.onChangeDates();
    closeDialog();
  };

  const submitBooking = async () => {
    if (isSubmitting.value) {
      return;
    }

    clearBookingFeedback();
    const validation = validate();
    if (validation) {
      bookingErrorMessage.value = validation;
      return;
    }

    const result = await bookingStore.submitBooking(buildRequest());
    if (result) {
      bookingStore.clearDraft();
      await router.push({
        name: "BookingConfirmation",
        params: { bookingId: result.bookingId },
      });
      options.onCompleted();
    }
  };

  watch(options.isOpen, (open) => {
    if (!open) {
      bookingStore.resetBookingFlow();
      return;
    }

    clearBookingFeedback();
    bookingStep.value = "details";
    bookingStore.updateDraft({
      roomId: options.roomId(),
      checkInDate: options.checkInDate(),
      checkOutDate: options.checkOutDate(),
      guestCount: draft.value.guestCount ?? 1,
      breakfastIncluded: draft.value.breakfastIncluded ?? false,
    });
  });

  return {
    draft,
    isSubmitting,
    bookingStep,
    dateRangeLabel,
    guestCountOptions,
    bookingMessage,
    stepContent,
    setDraftValue,
    proceedToReview,
    backToDetails,
    requestChangeDates,
    submitBooking,
    closeDialog,
  };
}
