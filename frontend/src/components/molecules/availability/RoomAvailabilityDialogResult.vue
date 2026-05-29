<script setup lang="ts">
import { computed } from "vue"
import { IonButton, IonChip, IonIcon } from "@ionic/vue"
import { bedOutline, calendarOutline, swapHorizontalOutline } from "ionicons/icons"
import { availabilityDialogContent } from "@/data/content/bookingContent"

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

const resultHint = computed(() =>
  props.isUnavailable
    ? availabilityDialogContent.result.unavailableHint
    : availabilityDialogContent.result.availableHint
)

const handleBook = () => {
  emit("book")
}

const handleChange = () => {
  emit("change")
}
</script>

<template>
  <div
    class="availability__result"
    :class="{
      'availability__result--success': !props.isUnavailable,
      'availability__result--warning': props.isUnavailable,
    }"
  >
    <div class="availability__header-row">
      <ion-chip :class="props.status.class">
        <ion-icon :icon="props.status.icon" />
        <span>{{ props.status.label }}</span>
      </ion-chip>
    </div>

    <p class="availability__message">{{ props.message }}</p>

    <div
      v-if="props.dateRangeLabel"
      class="availability__dates-panel"
      :class="{ 'availability__dates-panel--warning': props.isUnavailable }"
    >
      <p class="availability__dates-label">{{ availabilityDialogContent.result.selectedDatesLabel }}</p>
      <p class="availability__dates">
        <ion-icon :icon="calendarOutline" />
        {{ props.dateRangeLabel }}
      </p>
    </div>

    <p class="availability__support">{{ resultHint }}</p>

    <div class="availability__actions">
      <ion-button fill="clear" class="availability__change" @click="handleChange">
        <template v-slot:start>
<ion-icon :icon="swapHorizontalOutline"  />
</template>
        {{ props.changeLabel }}
      </ion-button>

      <ion-button v-if="props.canBook" class="availability__book" @click="handleBook">
        <template v-slot:start>
<ion-icon :icon="bedOutline"  />
</template>
        {{ props.bookNowLabel }}
      </ion-button>
    </div>
  </div>
</template>

<style scoped>
.availability__result {
  display: grid;
  gap: 10px;
  margin-top: 14px;
  padding: 16px;
  border-radius: 12px;
  background: var(--color-cream);
  border: 1px solid rgba(124, 84, 65, 0.14);
  box-shadow: 0 12px 26px rgba(86, 57, 40, 0.08);
}

.availability__result--success {
  background: linear-gradient(180deg, rgba(249, 243, 232, 0.98), rgba(242, 234, 221, 0.94));
}

.availability__result--warning {
  background: linear-gradient(180deg, rgba(255, 247, 241, 0.98), rgba(250, 238, 232, 0.96));
}

.availability__header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.availability__status {
  width: fit-content;
  font-weight: 600;
}

.availability__status,
.availability__status span,
.availability__header-row ion-chip span {
  white-space: nowrap;
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
.availability__support {
  margin: 0;
  font-size: 0.95rem;
  line-height: 1.5;
}

.availability__dates-panel {
  display: grid;
  gap: 4px;
  padding: 12px;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.6);
  border: 1px solid rgba(124, 84, 65, 0.1);
}

.availability__dates {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--color-olive);
  font-weight: 600;
}

.availability__dates-label {
  margin: 0;
  font-size: 0.8rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: var(--color-olive);
  font-weight: 700;
}

.availability__dates-panel--warning {
  background: rgba(255, 250, 247, 0.8);
}

.availability__dates-panel--warning .availability__dates-label,
.availability__dates-panel--warning .availability__dates {
  color: var(--color-terracotta);
}

.availability__dates-panel--warning .availability__dates ion-icon {
  color: var(--color-terracotta);
}

.availability__support {
  color: var(--color-olive);
}

.availability__actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.availability__book {
  width: fit-content;
}

.availability__change {
  width: fit-content;
}

.availability__change ion-icon {
  font-size: 1rem;
  margin-inline-end: 4px;
  flex-shrink: 0;
}

@media (max-width: 640px) {
  .availability__book {
    width: auto;
    flex: 1 1 0;
  }

  .availability__change {
    width: auto;
    flex: 1 1 0;
  }

  .availability__actions {
    position: fixed;
    left: 16px;
    right: 16px;
    bottom: calc(10px + env(safe-area-inset-bottom, 0px));
    z-index: 80;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-end;
    background: transparent;
    backdrop-filter: none;
    padding: 0;
    border: none;
    border-radius: 0;
    box-shadow: none;
  }

  .availability__actions ion-button {
    margin: 0;
    min-width: 0;
  }

  .availability__book,
  .availability__change {
    width: 100%;
    min-height: 46px;
  }

  .availability__result {
    padding-bottom: calc(92px + env(safe-area-inset-bottom, 0px));
  }
}
</style>
