import { computed, ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { checkmarkCircleOutline, closeCircleOutline } from "ionicons/icons";
import { useAvailabilityStore } from "@/application/stores/availabilityStore";
import { availabilityDialogContent } from "@/data/content/bookingContent";
import {
  formatDate,
  getTodayIsoDate,
  toIsoDate,
  validateDateRange,
} from "@/core/dateutils";

export interface UseAvailabilityFlowOptions {
  isOpen: () => boolean;
  roomId: () => number;
  onClose: () => void;
}

export function useAvailabilityFlow(options: UseAvailabilityFlowOptions) {
  const availabilityStore = useAvailabilityStore();
  const { availabilityByRoomId, error } = storeToRefs(availabilityStore);

  const isRoomLoading = computed(
    () => availabilityStore.loadingByRoomId[options.roomId()] ?? false
  );

  const isBookingOpen = ref(false);
  const step = ref<"form" | "result">("form");
  const checkInDate = ref<string | undefined>(undefined);
  const checkOutDate = ref<string | undefined>(undefined);
  const localError = ref("");

  const availability = computed(
    () => availabilityByRoomId.value[options.roomId()]
  );
  const minCheckIn = computed(() => getTodayIsoDate());
  const checkInValue = computed(() => toIsoDate(checkInDate.value));
  const checkOutValue = computed(() => toIsoDate(checkOutDate.value));
  const minCheckOut = computed(() => checkInValue.value || getTodayIsoDate());
  const errorMessage = computed(() => localError.value);

  const availabilityStatus = computed(() => {
    const available = !!availability.value?.available;
    return {
      label: available
        ? availabilityDialogContent.availableLabel
        : availabilityDialogContent.unavailableLabel,
      icon: available ? checkmarkCircleOutline : closeCircleOutline,
      class: available
        ? "availability__status availability__status--success"
        : "availability__status availability__status--warning",
    };
  });

  const dateRangeLabel = computed(() =>
    checkInValue.value && checkOutValue.value
      ? `${formatDate(checkInValue.value)} - ${formatDate(checkOutValue.value)}`
      : ""
  );

  const canBook = computed(() =>
    Boolean(
      availability.value?.available &&
        checkInValue.value &&
        checkOutValue.value
    )
  );
  const isUnavailable = computed(
    () => availability.value && !availability.value.available
  );

  const clearFeedback = () => {
    localError.value = "";
    step.value = "form";
  };

  const showForm = () => {
    step.value = "form";
  };

  const openBookingDialog = () => {
    isBookingOpen.value = true;
  };

  const closeBookingDialog = () => {
    isBookingOpen.value = false;
  };

  const closeDialog = () => {
    clearFeedback();
    availabilityStore.resetAvailabilityFlow(options.roomId());
    closeBookingDialog();
    options.onClose();
  };

  const closeAvailabilityAfterBooking = () => {
    closeBookingDialog();
    clearFeedback();
    options.onClose();
  };

  const handleCheck = async () => {
    clearFeedback();
    const validation = validateDateRange(checkInValue.value, checkOutValue.value);
    if (validation) {
      localError.value = validation;
      return;
    }

    const existing = availability.value;
    if (
      existing &&
      existing.checkInDate === checkInValue.value &&
      existing.checkOutDate === checkOutValue.value
    ) {
      step.value = "result";
      return;
    }

    const result = await availabilityStore.checkRoomAvailability(
      options.roomId(),
      checkInValue.value,
      checkOutValue.value
    );

    if (!result) {
      localError.value = error.value || availabilityDialogContent.unavailableMessage;
      return;
    }

    step.value = "result";
  };

  const requestChangeDates = () => {
    showForm();
    closeBookingDialog();
  };

  watch([checkInDate, checkOutDate], () => {
    localError.value = "";
    if (availability.value) {
      availabilityStore.clearAvailability(options.roomId());
    }
    step.value = "form";
    if (isBookingOpen.value) {
      closeBookingDialog();
    }
  });

  watch(options.isOpen, (open) => {
    if (open) {
      localError.value = "";
      step.value = "form";
      return;
    }

    clearFeedback();
    availabilityStore.resetAvailabilityFlow(options.roomId());
    closeBookingDialog();
  });

  return {
    isBookingOpen,
    step,
    checkInDate,
    checkOutDate,
    isRoomLoading,
    availability,
    minCheckIn,
    minCheckOut,
    checkInValue,
    checkOutValue,
    errorMessage,
    availabilityStatus,
    dateRangeLabel,
    canBook,
    isUnavailable,
    handleCheck,
    showForm,
    openBookingDialog,
    closeBookingDialog,
    closeDialog,
    closeAvailabilityAfterBooking,
    requestChangeDates,
  };
}
