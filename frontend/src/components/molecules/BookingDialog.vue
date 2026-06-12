<script setup lang="ts">
import BasePopup from "@/components/atoms/BasePopup.vue"
import { bookingDialogContent } from "@/data/content/bookingContent"
import DialogHeader from "@/components/molecules/shared/DialogHeader.vue"
import BookingDialogDetailsForm from "@/components/molecules/booking/BookingDialogDetailsForm.vue"
import BookingDialogReview from "@/components/molecules/booking/BookingDialogReview.vue"
import { useBookingFlow } from "@/application/composables/useBookingFlow"

const props = defineProps<{
  isOpen: boolean
  roomId: number
  roomTitle: string
  roomMaxGuests: number
  checkInDate: string
  checkOutDate: string
}>()

const emit = defineEmits<{
  (event: "close"): void
  (event: "completed"): void
  (event: "change-dates"): void
}>()

const {
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
} = useBookingFlow({
  isOpen: () => props.isOpen,
  roomId: () => props.roomId,
  roomMaxGuests: () => props.roomMaxGuests,
  checkInDate: () => props.checkInDate,
  checkOutDate: () => props.checkOutDate,
  onClose: () => emit("close"),
  onCompleted: () => emit("completed"),
  onChangeDates: () => emit("change-dates"),
})
</script>

<template>
  <base-popup
    :is-open="props.isOpen"
    content-class="dialog-shell"
    modal-class="dialog-modal"
    @close="closeDialog"
  >
      <dialog-header
        :eyebrow="bookingDialogContent.eyebrow"
        :title="props.roomTitle"
        :subtitle="dateRangeLabel"
        :close-label="bookingDialogContent.buttons.close"
        @close="closeDialog"
      />

      <booking-dialog-details-form
        v-if="bookingStep === 'details'"
        :step-content="stepContent"
        :draft="draft"
        :guest-count-options="guestCountOptions"
        :room-max-guests="roomMaxGuests"
        :booking-message="bookingMessage"
        @update-field="setDraftValue"
        @proceed="proceedToReview"
        @change-dates="requestChangeDates"
      />

      <booking-dialog-review
        v-else-if="bookingStep === 'review'"
        :step-content="stepContent"
        :draft="draft"
        :date-range-label="dateRangeLabel"
        :booking-message="bookingMessage"
        :is-submitting="isSubmitting"
        @back="backToDetails"
        @submit="submitBooking"
      />
  </base-popup>
</template>

<style scoped src="./shared/dialog-shell.css"></style>
