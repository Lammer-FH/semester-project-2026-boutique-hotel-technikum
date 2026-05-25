<script setup lang="ts">
import { computed, ref, watch } from "vue"
import BasePopup from "@/components/atoms/BasePopup.vue"
import { storeToRefs } from "pinia"
import { useBookingStore } from "@/application/stores/bookingStore"
import { formatDate, validateDateRange } from "@/core/dateutils"
import { bookingDialogContent } from "@/data/content/bookingContent"
import type { BookingRequest } from "@/core/models/booking"
import DialogHeader from "@/components/molecules/shared/DialogHeader.vue"
import BookingDialogDetailsForm from "@/components/molecules/booking/BookingDialogDetailsForm.vue"
import BookingDialogReview from "@/components/molecules/booking/BookingDialogReview.vue"
import BookingDialogConfirmation from "@/components/molecules/booking/BookingDialogConfirmation.vue"

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
}>()

const bookingStore = useBookingStore()
const { currentBooking, draft, isSubmitting, error } = storeToRefs(bookingStore)

const bookingErrorMessage = ref("")
const bookingStep = ref<"details" | "review" | "confirmation">("details")

const dateRangeLabel = computed(() => {
  return props.checkInDate && props.checkOutDate
    ? `${formatDate(props.checkInDate)} - ${formatDate(props.checkOutDate)}`
    : ""
})

const guestCountOptions = computed(() =>
  Array.from({ length: props.roomMaxGuests }, (_, index) => index + 1)
)

const bookingMessage = computed(() => bookingErrorMessage.value || error.value || "")
const stepContent = computed(() => {
  const key = bookingStep.value === "details" ? "details" : "review"
  return {
    label: bookingDialogContent.stepLabel[key],
    title: bookingDialogContent.stepTitle[key],
    hint: bookingDialogContent.hints[key],
  }
})
const bookingIdText = computed(() => {
  return currentBooking.value
    ? bookingDialogContent.confirmation.bookingId(currentBooking.value.bookingId)
    : ""
})

const clearBookingFeedback = () => {
  bookingErrorMessage.value = ""
  bookingStore.clearBooking()
}

const closeDialog = () => {
  bookingStep.value = "details"
  clearBookingFeedback()
  bookingStore.clearDraft()
  emit("close")
}

const setDraftValue = <K extends keyof BookingRequest>(
  key: K,
  value: BookingRequest[K]
) => {
  bookingStore.updateDraft({ [key]: value } as Partial<BookingRequest>)
}

const buildRequest = (): BookingRequest => ({
  roomId: props.roomId,
  guestFirstName: draft.value.guestFirstName ?? "",
  guestLastName: draft.value.guestLastName ?? "",
  guestEmail: draft.value.guestEmail ?? "",
  confirmEmail: draft.value.confirmEmail ?? "",
  guestCount: Number(draft.value.guestCount ?? 1),
  checkInDate: props.checkInDate,
  checkOutDate: props.checkOutDate,
  breakfastIncluded: Boolean(draft.value.breakfastIncluded),
})

const validateBookingDetails = () => {
  const dateError = validateDateRange(props.checkInDate, props.checkOutDate)
  if (dateError) {
    return dateError
  }

  const firstName = draft.value.guestFirstName?.trim()
  const lastName = draft.value.guestLastName?.trim()
  const email = draft.value.guestEmail?.trim()
  const confirmEmail = draft.value.confirmEmail?.trim()

  if (!firstName || !lastName) {
    return bookingDialogContent.errors.nameMissing
  }

  if (!email || !confirmEmail) {
    return bookingDialogContent.errors.emailMissing
  }

  if (email !== confirmEmail) {
    return bookingDialogContent.errors.emailMismatch
  }

  const guests = Number(draft.value.guestCount ?? 1)
  if (Number.isNaN(guests) || guests < 1) {
    return bookingDialogContent.errors.guestCountInvalid
  }

  if (guests > props.roomMaxGuests) {
    return bookingDialogContent.errors.maxGuestsExceeded(props.roomMaxGuests)
  }

  return null
}

const proceedToReview = () => {
  clearBookingFeedback()
  const validation = validateBookingDetails()
  if (validation) {
    bookingErrorMessage.value = validation
    return
  }

  bookingStep.value = "review"
}

const submitBooking = async () => {
  clearBookingFeedback()
  const validation = validateBookingDetails()
  if (validation) {
    bookingErrorMessage.value = validation
    return
  }

  const result = await bookingStore.submitBooking(buildRequest())
  if (result) {
    bookingStep.value = "confirmation"
  }
}

const handleDone = () => {
  emit("completed")
  closeDialog()
}

watch(
  () => props.isOpen,
  (open) => {
    if (!open) {
      bookingStore.clearDraft()
      return
    }

    clearBookingFeedback()
    bookingStep.value = "details"
    bookingStore.updateDraft({
      roomId: props.roomId,
      checkInDate: props.checkInDate,
      checkOutDate: props.checkOutDate,
      guestCount: draft.value.guestCount ?? 1,
      breakfastIncluded: draft.value.breakfastIncluded ?? false,
    })
  }
)
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
        :title="roomTitle"
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
      />

      <booking-dialog-review
        v-else-if="bookingStep === 'review'"
        :step-content="stepContent"
        :draft="draft"
        :booking-message="bookingMessage"
        :is-submitting="isSubmitting"
        @back="bookingStep = 'details'"
        @submit="submitBooking"
      />

      <booking-dialog-confirmation
        v-else
        :title="bookingDialogContent.confirmation.title"
        :booking-id-text="bookingIdText"
        :date-range-label="dateRangeLabel"
        :done-label="bookingDialogContent.buttons.done"
        @done="handleDone"
      />
  </base-popup>
</template>

<style scoped src="./shared/dialog-shell.css"></style>
