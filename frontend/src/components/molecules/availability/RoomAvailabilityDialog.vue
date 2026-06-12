<script setup lang="ts">
import BasePopup from "@/components/atoms/BasePopup.vue"
import BookingDialog from "@/components/molecules/BookingDialog.vue"
import DialogHeader from "@/components/molecules/shared/DialogHeader.vue"
import RoomAvailabilityDialogDateForm from "@/components/molecules/availability/RoomAvailabilityDialogDateForm.vue"
import RoomAvailabilityDialogResult from "@/components/molecules/availability/RoomAvailabilityDialogResult.vue"
import { availabilityDialogContent } from "@/data/content/bookingContent"
import { useAvailabilityFlow } from "@/application/composables/useAvailabilityFlow"

const props = defineProps<{
  isOpen: boolean
  roomId: number
  roomTitle: string
  roomMaxGuests: number
}>()

const emit = defineEmits<{
  (event: "close"): void
}>()

const {
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
} = useAvailabilityFlow({
  isOpen: () => props.isOpen,
  roomId: () => props.roomId,
  onClose: () => emit("close"),
})
</script>

<template>
  <div class="availability">
    <base-popup
      :is-open="props.isOpen"
      content-class="dialog-shell"
      modal-class="dialog-modal"
      @close="closeDialog"
    >
        <div class="availability-dialog__body">
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
            :check-in-placeholder="availabilityDialogContent.checkInPlaceholder"
            :check-out-placeholder="availabilityDialogContent.checkOutPlaceholder"
            :min-check-in="minCheckIn"
            :min-check-out="minCheckOut"
            :is-loading="isRoomLoading"
            :checking-label="availabilityDialogContent.checkingLabel"
            :confirm-label="availabilityDialogContent.confirmLabel"
            :error-message="errorMessage"
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
        </div>
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
        @change-dates="requestChangeDates"
      />
  </div>
</template>

<style scoped src="../shared/dialog-shell.css"></style>

<style scoped>
.availability-dialog__body {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

@media (max-width: 640px) {
  .availability-dialog__body {
    min-height: 100%;
  }
}
</style>
