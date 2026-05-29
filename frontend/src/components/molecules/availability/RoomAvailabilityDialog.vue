<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { storeToRefs } from "pinia"
import { checkmarkCircleOutline, closeCircleOutline } from "ionicons/icons"
import { useAvailabilityStore } from "@/application/stores/availabilityStore"
import BasePopup from "@/components/atoms/BasePopup.vue"
import BookingDialog from "@/components/molecules/BookingDialog.vue"
import DialogHeader from "@/components/molecules/shared/DialogHeader.vue"
import RoomAvailabilityDialogDateForm from "@/components/molecules/availability/RoomAvailabilityDialogDateForm.vue"
import RoomAvailabilityDialogResult from "@/components/molecules/availability/RoomAvailabilityDialogResult.vue"
import { availabilityDialogContent } from "@/data/content/bookingContent"
import {
  formatDate,
  getTodayIsoDate,
  toIsoDate,
  validateDateRange,
} from "@/core/dateutils"

const props = defineProps<{
  isOpen: boolean
  roomId: number
  roomTitle: string
  roomMaxGuests: number
}>()

const emit = defineEmits<{
  (event: "close"): void
}>()

const availabilityStore = useAvailabilityStore()
const { availabilityByRoomId, error } = storeToRefs(availabilityStore)
const isRoomLoading = computed(() => availabilityStore.loadingByRoomId[props.roomId] ?? false)

const isBookingOpen = ref(false)
const step = ref<"form" | "result">("form")
const checkInDate = ref<string | undefined>(undefined)
const checkOutDate = ref<string | undefined>(undefined)
const localError = ref("")

const availability = computed(() => availabilityByRoomId.value[props.roomId])
const minCheckIn = computed(() => getTodayIsoDate())
const checkInValue = computed(() => toIsoDate(checkInDate.value))
const checkOutValue = computed(() => toIsoDate(checkOutDate.value))
const minCheckOut = computed(() => checkInValue.value || getTodayIsoDate())

const errorMessage = computed(() => localError.value)

const availabilityStatus = computed(() => {
  const available = !!availability.value?.available
  return {
    label: available
      ? availabilityDialogContent.availableLabel
      : availabilityDialogContent.unavailableLabel,
    icon: available ? checkmarkCircleOutline : closeCircleOutline,
    class: available
      ? "availability__status availability__status--success"
      : "availability__status availability__status--warning",
  }
})

const dateRangeLabel = computed(() => {
  return checkInValue.value && checkOutValue.value
    ? `${formatDate(checkInValue.value)} - ${formatDate(checkOutValue.value)}`
    : ""
})

const canBook = computed(
  () => Boolean(availability.value?.available && checkInValue.value && checkOutValue.value)
)
const isUnavailable = computed(() => availability.value && !availability.value.available)

const clearFeedback = () => {
  localError.value = ""
  step.value = "form"
}

const closeDialog = () => {
  clearFeedback()
  availabilityStore.resetAvailabilityFlow(props.roomId)
  closeBookingDialog()
  emit("close")
}

const handleCheck = async () => {
  clearFeedback()
  const validation = validateDateRange(checkInValue.value, checkOutValue.value)
  if (validation) {
    localError.value = validation
    return
  }

  const existing = availability.value
  if (
    existing &&
    existing.checkInDate === checkInValue.value &&
    existing.checkOutDate === checkOutValue.value
  ) {
    step.value = "result"
    return
  }

  const result = await availabilityStore.checkRoomAvailability(
    props.roomId,
    checkInValue.value,
    checkOutValue.value
  )

  if (!result) {
    localError.value = error.value || availabilityDialogContent.unavailableMessage
    return
  }

  step.value = "result"
}

const showForm = () => {
  step.value = "form"
}

const openBookingDialog = () => {
  isBookingOpen.value = true
}

const closeBookingDialog = () => {
  isBookingOpen.value = false
}

const closeAvailabilityAfterBooking = () => {
  closeBookingDialog()
  clearFeedback()
  emit("close")
}

watch([checkInDate, checkOutDate], () => {
  localError.value = ""
  if (availability.value) {
    availabilityStore.clearAvailability(props.roomId)
  }
  step.value = "form"
  if (isBookingOpen.value) {
    closeBookingDialog()
  }
})

watch(
  () => props.isOpen,
  (open) => {
    if (open) {
      localError.value = ""
      step.value = "form"
      return
    }

    clearFeedback()
    availabilityStore.resetAvailabilityFlow(props.roomId)
    closeBookingDialog()
  }
)
</script>

<template>
  <div class="availability">
    <base-popup
      :is-open="props.isOpen"
      content-class="dialog-shell"
      modal-class="dialog-modal"
      @close="closeDialog"
    >
        <dialog-header
          :eyebrow="availabilityDialogContent.title"
          :title="props.roomTitle"
          :close-label="availabilityDialogContent.closeLabel"
          @close="closeDialog"
        />

        <room-availability-dialog-date-form
          v-if="step === 'form'"
          v-model:check-in-date="checkInDate"
          v-model:check-out-date="checkOutDate"
          :check-in-label="availabilityDialogContent.checkInLabel"
          :check-out-label="availabilityDialogContent.checkOutLabel"
          :min-check-in="minCheckIn"
          :min-check-out="minCheckOut"
          :is-loading="isRoomLoading"
          :checking-label="availabilityDialogContent.checkingLabel"
          :confirm-label="availabilityDialogContent.confirmLabel"
          @check="handleCheck"
        />

        <room-availability-dialog-result
          v-else-if="availability"
          :status="availabilityStatus"
          :message="availability.message"
          :date-range-label="dateRangeLabel"
          :is-unavailable="isUnavailable"
          :can-book="canBook"
          :book-now-label="availabilityDialogContent.bookNowLabel"
          :change-label="availabilityDialogContent.changeDatesLabel"
          @book="openBookingDialog"
          @change="showForm"
        />

        <p v-if="step === 'form' && errorMessage" class="availability__error">
          {{ errorMessage }}
        </p>
    </base-popup>

    <booking-dialog
        :is-open="isBookingOpen"
        :room-id="props.roomId"
        :room-title="props.roomTitle"
        :room-max-guests="props.roomMaxGuests"
        :check-in-date="checkInValue"
        :check-out-date="checkOutValue"
        @close="closeBookingDialog"
        @completed="closeAvailabilityAfterBooking"
        @change-dates="() => { showForm(); closeBookingDialog(); }"
      />
  </div>
</template>

<style scoped src="../shared/dialog-shell.css"></style>

<style scoped>
.availability__error {
  margin: 8px 0 0;
  font-size: 0.95rem;
  color: var(--color-terracotta);
  font-weight: 600;
}
</style>
