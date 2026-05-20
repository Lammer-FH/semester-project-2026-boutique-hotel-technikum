<script setup lang="ts">
import { IonButton, IonIcon } from "@ionic/vue"
import { calendarOutline } from "ionicons/icons"
import BaseDatePicker from "@/components/atoms/BaseDatePicker.vue"

const props = defineProps<{
  checkInLabel: string
  checkOutLabel: string
  minCheckIn: string
  minCheckOut: string
  checkInDate?: string
  checkOutDate?: string
  isLoading: boolean
  checkingLabel: string
  confirmLabel: string
}>()

const emit = defineEmits<{
  (event: "update:checkInDate", value?: string): void
  (event: "update:checkOutDate", value?: string): void
  (event: "check"): void
}>()

const handleCheckInUpdate = (value?: string) => {
  emit("update:checkInDate", value)
}

const handleCheckOutUpdate = (value?: string) => {
  emit("update:checkOutDate", value)
}

const handleCheck = () => {
  emit("check")
}
</script>

<template>
  <div>
    <div class="availability-dialog__grid">
      <base-date-picker
        :label="props.checkInLabel"
        :min="props.minCheckIn"
        :model-value="props.checkInDate"
        @update:modelValue="handleCheckInUpdate"
      />
      <base-date-picker
        :label="props.checkOutLabel"
        :min="props.minCheckOut"
        :model-value="props.checkOutDate"
        @update:modelValue="handleCheckOutUpdate"
      />
    </div>

    <div class="availability-dialog__actions">
      <ion-button :disabled="props.isLoading" @click="handleCheck">
        <ion-icon slot="start" :icon="calendarOutline" />
        {{ props.isLoading ? props.checkingLabel : props.confirmLabel }}
      </ion-button>
    </div>
  </div>
</template>

<style scoped>
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
  .availability-dialog__actions {
    justify-content: stretch;
  }

  .availability-dialog__actions ion-button {
    width: 100%;
  }
}
</style>
