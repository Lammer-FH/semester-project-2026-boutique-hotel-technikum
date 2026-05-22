<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { IonButton, IonIcon } from "@ionic/vue"
import { checkmarkCircleOutline, closeCircleOutline, calendarOutline } from "ionicons/icons"
import { storeToRefs } from "pinia"
import { useAvailabilityStore } from "@/application/stores/availabilityStore"
import BasePopup from "@/components/atoms/BasePopup.vue"
import BookingDialog from "@/components/molecules/BookingDialog.vue"
import DialogHeader from "@/components/molecules/shared/DialogHeader.vue"
import RoomAvailabilityDialogDateForm from "@/components/molecules/availability/RoomAvailabilityDialogDateForm.vue"
import RoomAvailabilityDialogResult from "@/components/molecules/availability/RoomAvailabilityDialogResult.vue"
import { availabilityDialogContent } from "@/data/hotelContent"
import {
  formatDate,
  getTodayIsoDate,
  toIsoDate,
  validateDateRange,
} from "@/core/dateutils"

const props = defineProps<{
  roomId: number
  roomTitle: string
  roomMaxGuests: number
}>()

const availabilityStore = useAvailabilityStore()
const { availabilityByRoomId, isLoading, error } = storeToRefs(availabilityStore)

const isOpen = ref(false)
const isBookingOpen = ref(false)
const checkInDate = ref<string | undefined>(undefined)
const checkOutDate = ref<string | undefined>(undefined)
const localError = ref("")

const availability = computed(() => availabilityByRoomId.value[props.roomId])
const minCheckIn = computed(() => getTodayIsoDate())
const checkInValue = computed(() => toIsoDate(checkInDate.value))
const checkOutValue = computed(() => toIsoDate(checkOutDate.value))
const minCheckOut = computed(() => checkInValue.value || getTodayIsoDate())

const errorMessage = computed(() => localError.value)

// availability status bundle
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

const openDialog = () => {
  isOpen.value = true
  localError.value = ""
}

const closeDialog = () => {
  isOpen.value = false
  clearFeedback()
  closeBookingDialog()
}

const clearFeedback = () => {
  localError.value = ""
  availabilityStore.clearAvailability(props.roomId)
}

const handleCheck = async () => {
  clearFeedback()
  const validation = validateDateRange(checkInValue.value, checkOutValue.value)
  if (validation) {
    localError.value = validation
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
}

const openBookingDialog = () => {
  isBookingOpen.value = true
}

const closeBookingDialog = () => {
  isBookingOpen.value = false
}

const closeAvailabilityAfterBooking = () => {
  isOpen.value = false
  clearFeedback()
}

watch([checkInDate, checkOutDate], () => {
  clearFeedback()
  if (isBookingOpen.value) {
    closeBookingDialog()
  }
})
</script>

<template>
  <div class="availability">
    <ion-button @click="openDialog">
      <ion-icon slot="start" :icon="calendarOutline" />
      {{ availabilityDialogContent.triggerLabel }}
    </ion-button>

    <base-popup
      :is-open="isOpen"
      content-class="availability-dialog"
      @close="closeDialog"
    >
        <dialog-header
          :eyebrow="availabilityDialogContent.title"
          :title="roomTitle"
          :close-label="availabilityDialogContent.closeLabel"
          @close="closeDialog"
        />

        <room-availability-dialog-date-form
          v-model:check-in-date="checkInDate"
          v-model:check-out-date="checkOutDate"
          :check-in-label="availabilityDialogContent.checkInLabel"
          :check-out-label="availabilityDialogContent.checkOutLabel"
          :min-check-in="minCheckIn"
          :min-check-out="minCheckOut"
          :is-loading="isLoading"
          :checking-label="availabilityDialogContent.checkingLabel"
          :confirm-label="availabilityDialogContent.confirmLabel"
          @check="handleCheck"
        />

        <room-availability-dialog-result
          v-if="availability"
          :status="availabilityStatus"
          :message="availability.message"
          :date-range-label="dateRangeLabel"
          :is-unavailable="isUnavailable"
          :can-book="canBook"
          :book-now-label="availabilityDialogContent.bookNowLabel"
          @book="openBookingDialog"
        />
        <p v-if="errorMessage" class="availability__error">
          {{ errorMessage }}
        </p>
    </base-popup>

    <booking-dialog
      :is-open="isBookingOpen"
      :room-id="roomId"
      :room-title="roomTitle"
      :room-max-guests="roomMaxGuests"
      :check-in-date="checkInValue"
      :check-out-date="checkOutValue"
      @close="closeBookingDialog"
      @completed="closeAvailabilityAfterBooking"
    />
  </div>
</template>

<style scoped>
.availability {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.availability__error {
  margin: 8px 0 0;
  font-size: 0.95rem;
  color: var(--color-terracotta);
  font-weight: 600;
}

:deep(.availability-dialog) {
  --padding-start: 18px;
  --padding-end: 18px;
  --padding-top: 18px;
  --padding-bottom: 24px;
}
</style>