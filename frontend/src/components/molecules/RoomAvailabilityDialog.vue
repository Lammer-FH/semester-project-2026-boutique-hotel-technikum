<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { IonButton, IonChip, IonIcon } from "@ionic/vue"
import { checkmarkCircleOutline, closeCircleOutline, calendarOutline } from "ionicons/icons"
import { storeToRefs } from "pinia"
import { useAvailabilityStore } from "@/application/stores/availabilityStore"
import BasePopup from "@/components/atoms/BasePopup.vue"
import BaseDatePicker from "@/components/atoms/BaseDatePicker.vue"
import BookingDialog from "@/components/molecules/BookingDialog.vue"
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
        <div class="availability-dialog__header">
          <div>
            <p class="availability-dialog__eyebrow">{{ availabilityDialogContent.title }}</p>
            <h3 class="availability-dialog__title">{{ roomTitle }}</h3>
          </div>
          <ion-button fill="clear" @click="closeDialog">
            {{ availabilityDialogContent.closeLabel }}
          </ion-button>
        </div>

        <div class="availability-dialog__grid">
          <base-date-picker
            :label="availabilityDialogContent.checkInLabel"
            :min="minCheckIn"
            v-model="checkInDate"
          />
          <base-date-picker
            :label="availabilityDialogContent.checkOutLabel"
            :min="minCheckOut"
            v-model="checkOutDate"
          />
        </div>

        <div class="availability-dialog__actions">
          <ion-button
            :disabled="isLoading"
            @click="handleCheck"
          >
            <ion-icon slot="start" :icon="calendarOutline" />
            {{ isLoading ? availabilityDialogContent.checkingLabel : availabilityDialogContent.confirmLabel }}
          </ion-button>
        </div>

        <div v-if="availability" class="availability__result">
          <ion-chip :class="availabilityStatus.class">
            <ion-icon :icon="availabilityStatus.icon" />
            <span>{{ availabilityStatus.label }}</span>
          </ion-chip>
          <p class="availability__message">{{ availability.message }}</p>
          <p
            v-if="dateRangeLabel"
            class="availability__dates"
            :class="{ 'availability__dates--warning': isUnavailable }"
          >
            <ion-icon :icon="calendarOutline" />
            {{ dateRangeLabel }}
          </p>
          <ion-button
            v-if="canBook"
            class="availability__book"
            @click="openBookingDialog"
          >
            {{ availabilityDialogContent.bookNowLabel }}
          </ion-button>
        </div>
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

.availability__result {
  display: grid;
  gap: 4px;
  margin-top: 14px;
  padding: 10px 12px;
  border-radius: 12px;
  background: var(--color-cream);
}

.availability__status {
  width: fit-content;
  font-weight: 600;
}

.availability__status--success {
  --background: rgba(60, 129, 90, 0.14);
  color: #2c6b4b;
}

.availability__status--warning {
  --background: rgba(161, 79, 54, 0.14);
  color: var(--color-terracotta);
}

.availability__message,
.availability__dates,
.availability__error {
  margin: 0;
  font-size: 0.95rem;
}

.availability__dates {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--color-olive);
  font-weight: 600;
}

.availability__dates--warning {
  color: var(--color-terracotta);
}

.availability__dates--warning ion-icon {
  color: var(--color-terracotta);
}
.availability__error {
  color: var(--color-terracotta);
  font-weight: 600;
}

.availability__book {
  margin-top: 8px;
  width: fit-content;
}

.availability-dialog {
  --padding-start: 18px;
  --padding-end: 18px;
  --padding-top: 18px;
  --padding-bottom: 24px;
}

.availability-dialog__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
}

.availability-dialog__eyebrow {
  margin: 0 0 4px;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
}

.availability-dialog__title {
  margin: 0;
  font-size: 1.4rem;
}

.availability-dialog__grid {
  display: grid;
  gap: 16px;
}


.availability-dialog__actions {
  margin-top: 18px;
  display: flex;
  justify-content: flex-end;
}

@media (min-width: 720px) {
  .availability-dialog__grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 640px) {
  .availability-dialog__header {
    flex-direction: column;
    align-items: flex-start;
  }

  .availability-dialog__actions {
    justify-content: stretch;
  }

  .availability-dialog__actions ion-button,
  .availability__book {
    width: 100%;
  }
}
</style>
