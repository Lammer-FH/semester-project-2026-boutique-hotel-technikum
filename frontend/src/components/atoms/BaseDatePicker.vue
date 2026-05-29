<script setup lang="ts">
import { IonDatetime, IonLabel } from "@ionic/vue"

const props = withDefaults(
  defineProps<{
    modelValue?: string
    label: string
    min?: string
    max?: string
    disabled?: boolean
    presentation?: "date" | "date-time" | "time" | "month"
  }>(),
  {
    disabled: false,
    presentation: "date",
  }
)

const emit = defineEmits<{
  (event: "update:modelValue", value?: string): void
}>()

const handleChange = (event: CustomEvent) => {
  emit("update:modelValue", event.detail.value as string | undefined)
}
</script>

<template>
  <div class="date-picker">
    <ion-label class="date-picker__label">{{ props.label }}</ion-label>
    <ion-datetime
      :presentation="props.presentation"
      :min="props.min"
      :max="props.max"
      :disabled="props.disabled"
      :value="props.modelValue"
      :first-day-of-week="1"
      @ionChange="handleChange"
    />
  </div>
</template>

<style scoped>
.date-picker {
  display: grid;
  gap: 8px;
}

.date-picker__label {
  font-weight: 600;
  color: var(--color-midnight);
}
</style>
