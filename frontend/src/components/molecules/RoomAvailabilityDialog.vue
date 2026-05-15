<script setup lang="ts">
import { computed, ref } from "vue"
import {
  IonButton,
  IonChip,
  IonDatetime,
  IonIcon,
  IonModal,
  IonSpinner,
} from "@ionic/vue"
import {
  calendarOutline,
  checkmarkCircleOutline,
  closeCircleOutline,
} from "ionicons/icons"
import { storeToRefs } from "pinia"
import { useAvailabilityStore } from "@/application/stores/availabilityStore"
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

const isOpen = ref(false)
const checkInDate = ref("")
const checkOutDate = ref("")
const validationError = ref("")
const requestError = ref("")

const availabilityStore = useAvailabilityStore()
const { availabilityByRoomId, isLoading, error } = storeToRefs(availabilityStore)

type DateChangeEvent = CustomEvent<{ value?: string | string[] | null }>

const availabilityResult = computed(
  () => availabilityByRoomId.value[props.roomId] ?? null
)

const minDate = computed(() => getTodayIsoDate())

const hasDates = computed(
  () => Boolean(checkInDate.value) && Boolean(checkOutDate.value)
)

const statusIcon = computed(() =>
  availabilityResult.value?.available
    ? checkmarkCircleOutline
    : closeCircleOutline
)

const statusClass = computed(() =>
  availabilityResult.value?.available
    ? "availability__status--available"
    : "availability__status--unavailable"
)

const rangeLabel = computed(() => {
  if (!hasDates.value) {
    return "Select a check-in and check-out date."
  }

  return `${formatDate(checkInDate.value)} - ${formatDate(checkOutDate.value)}`
})

const resetErrors = () => {
  validationError.value = ""
  requestError.value = ""
}

const openDialog = () => {
  resetErrors()
  isOpen.value = true
}

const closeDialog = () => {
  isOpen.value = false
}

const updateCheckInDate = (event: DateChangeEvent) => {
  checkInDate.value = toIsoDate(event.detail.value)
  resetErrors()
  availabilityStore.clearAvailability(props.roomId)
}

const updateCheckOutDate = (event: DateChangeEvent) => {
  checkOutDate.value = toIsoDate(event.detail.value)
  resetErrors()
  availabilityStore.clearAvailability(props.roomId)
}

const isDateRangeValid = () => {
  const errorMessage = validateDateRange(
    checkInDate.value,
    checkOutDate.value
  )

  if (errorMessage) {
    validationError.value = errorMessage
    return false
  }

  return true
}

const checkAvailability = async () => {
  resetErrors()

  if (!isDateRangeValid()) {
    return
  }

  const result = await availabilityStore.checkRoomAvailability(
    props.roomId,
    checkInDate.value,
    checkOutDate.value
  )

  if (!result) {
    requestError.value = error.value ?? "Unable to check availability."
  }
}
</script>

<template>
  <div class="availability">
    <div class="availability__row">
      <ion-button size="small" fill="outline" @click="openDialog">
        <ion-icon slot="start" :icon="calendarOutline" />
        Check availability
      </ion-button>
      <ion-chip
        v-if="availabilityResult"
        class="availability__status"
        :class="statusClass"
      >
        <ion-icon :icon="statusIcon" />
        <span>{{ availabilityResult.available ? "Available" : "Unavailable" }}</span>
      </ion-chip>
    </div>

    <ion-modal :is-open="isOpen" @didDismiss="closeDialog">
      <div class="availability__dialog">
        <div class="availability__header">
          <div>
            <div class="availability__eyebrow">Availability check</div>
            <h3 class="availability__title">{{ roomTitle }}</h3>
          </div>
          <ion-button fill="clear" size="small" @click="closeDialog">
            Close
          </ion-button>
        </div>

        <p class="availability__subtitle">
          Pick a travel period to see if this room can welcome you.
        </p>

        <div class="availability__dates">
          <div class="availability__field">
            <div class="availability__label">Check-in</div>
            <ion-datetime
              presentation="date"
              :min="minDate"
              :value="checkInDate"
              @ionChange="updateCheckInDate"
            />
          </div>
          <div class="availability__field">
            <div class="availability__label">Check-out</div>
            <ion-datetime
              presentation="date"
              :min="checkInDate || minDate"
              :value="checkOutDate"
              @ionChange="updateCheckOutDate"
            />
          </div>
        </div>

        <div class="availability__range">{{ rangeLabel }}</div>

        <div
          v-if="availabilityResult"
          class="availability__message"
          :class="statusClass"
        >
          <ion-icon :icon="statusIcon" />
          {{ availabilityResult.message }}
        </div>
        <div v-if="validationError || requestError" class="availability__message availability__message--error">
          <ion-icon :icon="closeCircleOutline" />
          {{ validationError || requestError }}
        </div>

        <div class="availability__actions">
          <ion-button
            :disabled="isLoading || !hasDates"
            @click="checkAvailability"
          >
            <ion-spinner v-if="isLoading" name="dots" />
            <span v-else>Check now</span>
          </ion-button>
        </div>
      </div>
    </ion-modal>
  </div>
</template>

<style scoped>
.availability {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.availability__row {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
}

.availability__status {
  --background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.1);
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 6px;
}

.availability__status--available {
  color: #3d6b45;
  border-color: rgba(61, 107, 69, 0.3);
}

.availability__status--unavailable {
  color: #a14f36;
  border-color: rgba(161, 79, 54, 0.3);
}

.availability__dialog {
  background: linear-gradient(150deg, #fff9f0 0%, #efe1cb 100%);
  padding: 20px;
  min-height: 100%;
}

.availability__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 12px;
}

.availability__eyebrow {
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
  font-weight: 600;
}

.availability__title {
  margin: 4px 0 0;
}

.availability__subtitle {
  margin-bottom: 16px;
  max-width: 420px;
}

.availability__dates {
  display: grid;
  gap: 16px;
}

.availability__field {
  background: #fff;
  border-radius: 16px;
  padding: 12px;
  border: 1px solid rgba(31, 27, 24, 0.1);
}

.availability__label {
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
  font-weight: 600;
  margin-bottom: 8px;
}

.availability__range {
  margin-top: 16px;
  font-weight: 600;
  color: var(--color-midnight);
}

.availability__message {
  margin-top: 14px;
  padding: 12px 14px;
  border-radius: 14px;
  background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.1);
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
}

.availability__message--error {
  color: var(--color-terracotta);
  border-color: rgba(161, 79, 54, 0.3);
  background: #fff4f0;
}

.availability__actions {
  margin-top: 18px;
  display: flex;
  justify-content: flex-end;
}

.availability__actions ion-button {
  min-width: 140px;
}

@media (min-width: 720px) {
  .availability__dates {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>
