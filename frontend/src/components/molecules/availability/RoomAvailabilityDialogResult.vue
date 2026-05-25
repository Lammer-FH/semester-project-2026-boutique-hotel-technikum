<script setup lang="ts">
import { IonButton, IonChip, IonIcon } from "@ionic/vue"
import { calendarOutline } from "ionicons/icons"

type AvailabilityStatus = {
  label: string
  icon: string
  class: string
}

const props = defineProps<{
  status: AvailabilityStatus
  message: string
  dateRangeLabel: string
  isUnavailable: boolean
  canBook: boolean
  bookNowLabel: string
  changeLabel: string
}>()

const emit = defineEmits<{
  (event: "book"): void
  (event: "change"): void
}>()

const handleBook = () => {
  emit("book")
}

const handleChange = () => {
  emit("change")
}
</script>

<template>
  <div class="availability__result">
    <ion-chip :class="props.status.class">
      <ion-icon :icon="props.status.icon" />
      <span>{{ props.status.label }}</span>
    </ion-chip>
    <p class="availability__message">{{ props.message }}</p>
    <p
      v-if="props.dateRangeLabel"
      class="availability__dates"
      :class="{ 'availability__dates--warning': props.isUnavailable }"
    >
      <ion-icon :icon="calendarOutline" />
      {{ props.dateRangeLabel }}
    </p>
    <ion-button v-if="props.canBook" class="availability__book" @click="handleBook">
      {{ props.bookNowLabel }}
    </ion-button>
    <ion-button fill="clear" size="small" class="availability__change" @click="handleChange">
      {{ props.changeLabel }}
    </ion-button>
  </div>
</template>

<style scoped>
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
.availability__dates {
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

.availability__book {
  margin-top: 8px;
  width: fit-content;
}

.availability__change {
  margin-top: 2px;
  width: fit-content;
  --padding-start: 0;
  --padding-end: 0;
}

@media (max-width: 640px) {
  .availability__book {
    width: 100%;
  }

  .availability__change {
    width: 100%;
  }
}
</style>
