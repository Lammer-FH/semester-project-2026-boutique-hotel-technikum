<script setup lang="ts">
import { computed } from "vue"
import { IonButton, IonIcon } from "@ionic/vue"
import { calendarOutline } from "ionicons/icons"
import BaseDatePicker from "@/components/atoms/BaseDatePicker.vue"

const props = defineProps<{
  checkInLabel: string
  checkOutLabel: string
  checkInPlaceholder: string
  checkOutPlaceholder: string
  minCheckIn: string
  minCheckOut: string
  checkInDate?: string
  checkOutDate?: string
  isLoading: boolean
  checkingLabel: string
  confirmLabel: string
  errorMessage?: string
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

const confirmText = computed(() =>
  props.isLoading ? props.checkingLabel : props.confirmLabel
)
</script>

<template>
  <div class="availability-dialog__form">
    <div class="availability-dialog__grid">
      <base-date-picker
        :label="props.checkInLabel"
        :placeholder="props.checkInPlaceholder"
        :min="props.minCheckIn"
        :model-value="props.checkInDate"
        @update:modelValue="handleCheckInUpdate"
      />
      <base-date-picker
        :label="props.checkOutLabel"
        :placeholder="props.checkOutPlaceholder"
        :min="props.minCheckOut"
        :model-value="props.checkOutDate"
        @update:modelValue="handleCheckOutUpdate"
      />
    </div>

    <p v-if="props.errorMessage" class="availability-dialog__error">
      {{ props.errorMessage }}
    </p>

    <div class="availability-dialog__actions">
      <ion-button expand="block" :disabled="props.isLoading" @click="handleCheck">
        <template v-slot:start>
          <ion-icon :icon="calendarOutline" />
        </template>
        {{ confirmText }}
      </ion-button>
    </div>
  </div>
</template>

<style scoped>
.availability-dialog__form {
  display: flex;
  flex-direction: column;
}

.availability-dialog__grid {
  display: grid;
  gap: 16px;
}

.availability-dialog__error {
  margin: 12px 0 0;
  font-size: 0.95rem;
  color: var(--color-terracotta);
  font-weight: 600;
}

.availability-dialog__actions {
  margin-top: 18px;
}

.availability-dialog__actions ion-button {
  margin: 0;
  min-height: 46px;
}

@media (min-width: 720px) {
  .availability-dialog__grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

/* On mobile, fill the dialog height and pin the button to the bottom. */
@media (max-width: 640px) {
  .availability-dialog__form {
    flex: 1;
  }

  .availability-dialog__actions {
    margin-top: auto;
    padding-top: 24px;
  }

  .availability-dialog__actions ion-button {
    min-height: 50px;
    font-size: 1rem;
  }
}
</style>
