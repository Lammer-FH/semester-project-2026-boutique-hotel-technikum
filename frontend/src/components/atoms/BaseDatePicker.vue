<script setup lang="ts">
import { computed, ref } from "vue"
import { IonButton, IonDatetime, IonIcon, IonLabel, IonPopover } from "@ionic/vue"
import { calendarOutline } from "ionicons/icons"
import { formatDate, toIsoDate } from "@/core/dateutils"

const triggerId = `date-picker-${Math.random().toString(16).slice(2, 10)}`

const props = withDefaults(
  defineProps<{
    modelValue?: string
    label: string
    placeholder?: string
    min?: string
    max?: string
    disabled?: boolean
    presentation?: "date" | "date-time" | "time" | "month"
  }>(),
  {
    disabled: false,
    presentation: "date",
    placeholder: "Please choose a date",
  }
)

const emit = defineEmits<{
  (event: "update:modelValue", value?: string): void
}>()

const popover = ref<InstanceType<typeof IonPopover> | null>(null)

const hasValue = computed(() => Boolean(toIsoDate(props.modelValue)))
const displayText = computed(() =>
  hasValue.value ? formatDate(toIsoDate(props.modelValue)) : props.placeholder
)

const handleChange = (event: CustomEvent) => {
  emit("update:modelValue", event.detail.value as string | undefined)
  // Close the popover as soon as a date is selected.
  const popoverEl = popover.value?.$el as HTMLIonPopoverElement | undefined
  void popoverEl?.dismiss()
}
</script>

<template>
  <div class="date-picker">
    <ion-label class="date-picker__label">{{ props.label }}</ion-label>
    <ion-button
      :id="triggerId"
      class="date-picker__trigger"
      :class="{ 'date-picker__trigger--placeholder': !hasValue }"
      fill="outline"
      expand="block"
      :disabled="props.disabled"
    >
      <ion-icon slot="start" :icon="calendarOutline" aria-hidden="true" />
      {{ displayText }}
    </ion-button>
    <ion-popover
      ref="popover"
      :trigger="triggerId"
      :keep-contents-mounted="true"
      :show-backdrop="true"
    >
      <ion-datetime
        :presentation="props.presentation"
        :min="props.min"
        :max="props.max"
        :disabled="props.disabled"
        :value="props.modelValue"
        :first-day-of-week="1"
        @ionChange="handleChange"
      />
    </ion-popover>
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

.date-picker__trigger {
  --border-radius: var(--radius-sm, 8px);
  text-transform: none;
  font-weight: 600;
  margin: 0;
}

.date-picker__trigger--placeholder {
  font-weight: 500;
  opacity: 0.85;
}

ion-popover {
  --width: auto;
}
</style>
