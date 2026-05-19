<script setup lang="ts">
import { computed, ref, watch } from "vue"
import {
  IonButton,
  IonChip,
  IonContent,
  IonIcon,
  IonModal,
} from "@ionic/vue"
import { checkmarkCircleOutline, closeCircleOutline, calendarOutline } from "ionicons/icons"
import { storeToRefs } from "pinia"
import { useAvailabilityStore } from "@/application/stores/availabilityStore"
import DatePicker from "@/components/atoms/DatePicker.vue"
import {
  formatDate,
  getTodayIsoDate,
  toIsoDate,
  validateDateRange,
} from "@/core/dateutils"

const props = defineProps<{
  roomId: number
  roomTitle: string
}>()

const availabilityStore = useAvailabilityStore()
const { availabilityByRoomId, isLoading, error } = storeToRefs(availabilityStore)

const isOpen = ref(false)
const checkInDate = ref<string | undefined>(undefined)
const checkOutDate = ref<string | undefined>(undefined)
const localError = ref("")

const availability = computed(() => availabilityByRoomId.value[props.roomId])
const minCheckIn = computed(() => getTodayIsoDate())
const minCheckOut = computed(() => toIsoDate(checkInDate.value) || getTodayIsoDate())

const errorMessage = computed(() => localError.value)

// availability status bundle
const availabilityStatus = computed(() => {
  const available = !!availability.value?.available
  return {
    label: available ? "Available" : "Not available",
    icon: available ? checkmarkCircleOutline : closeCircleOutline,
    class: available
      ? "availability__status availability__status--success"
      : "availability__status availability__status--warning",
  }
})

const dateRangeLabel = computed(() => {
  const checkInValue = toIsoDate(checkInDate.value)
  const checkOutValue = toIsoDate(checkOutDate.value)
  return checkInValue && checkOutValue
    ? `${formatDate(checkInValue)} - ${formatDate(checkOutValue)}`
    : ""
})

const openDialog = () => {
  isOpen.value = true
  localError.value = ""
}

const closeDialog = () => {
  isOpen.value = false
  clearFeedback()
}

const clearFeedback = () => {
  localError.value = ""
  availabilityStore.clearAvailability(props.roomId)
}

const handleCheck = async () => {
  clearFeedback()
  const checkInValue = toIsoDate(checkInDate.value)
  const checkOutValue = toIsoDate(checkOutDate.value)
  const validation = validateDateRange(checkInValue, checkOutValue)
  if (validation) {
    localError.value = validation
    return
  }

  const result = await availabilityStore.checkRoomAvailability(
    props.roomId,
    checkInValue,
    checkOutValue
  )

  if (!result) {
    localError.value = error.value || "Unable to check availability."
    return
  }
}

watch([checkInDate, checkOutDate], clearFeedback)
</script>

<template>
  <div class="availability">
    <ion-button @click="openDialog">
      <ion-icon slot="start" :icon="calendarOutline" />
      Check availability
    </ion-button>

    <ion-modal :is-open="isOpen" :keep-contents-mounted="true" @didDismiss="closeDialog">
      <ion-content class="availability-dialog">
        <div class="availability-dialog__header">
          <div>
            <p class="availability-dialog__eyebrow">Check availability</p>
            <h3 class="availability-dialog__title">{{ roomTitle }}</h3>
          </div>
          <ion-button fill="clear" @click="closeDialog">Close</ion-button>
        </div>

        <div class="availability-dialog__grid">
          <date-picker
            label="Check-in"
            :min="minCheckIn"
            v-model="checkInDate"
          />
          <date-picker
            label="Check-out"
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
            {{ isLoading ? "Checking..." : "Check availability" }}
          </ion-button>
        </div>

        <div v-if="availability" class="availability__result">
          <ion-chip :class="availabilityStatus.class">
            <ion-icon :icon="availabilityStatus.icon" />
            <span>{{ availabilityStatus.label }}</span>
          </ion-chip>
          <p class="availability__message">{{ availability.message }}</p>
          <p v-if="dateRangeLabel" class="availability__dates">
            <ion-icon :icon="calendarOutline" />
            {{ dateRangeLabel }}
          </p>
        </div>
        <p v-if="errorMessage" class="availability__error">
          {{ errorMessage }}
        </p>
      </ion-content>
    </ion-modal>
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

.availability__error {
  color: var(--color-terracotta);
  font-weight: 600;
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
</style>
