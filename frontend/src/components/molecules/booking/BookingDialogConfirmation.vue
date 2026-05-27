<script setup lang="ts">
import { nextTick, onMounted, ref } from "vue"
import { IonButton, IonIcon } from "@ionic/vue"
import { calendarOutline, checkmarkCircleOutline, receiptOutline } from "ionicons/icons"
import { bookingDialogContent } from "@/data/content/bookingContent"

const props = defineProps<{
  title: string
  bookingIdText: string
  dateRangeLabel: string
  doneLabel: string
}>()

const emit = defineEmits<{
  (event: "done"): void
}>()

const handleDone = () => {
  emit("done")
}

const titleRef = ref<HTMLHeadingElement | null>(null)

onMounted(async () => {
  await nextTick()
  titleRef.value?.focus()
})
</script>

<template>
  <div class="booking-dialog__section">
    <div class="booking-confirmation">
      <div class="booking-confirmation__hero">
        <span class="booking-confirmation__badge">
          <ion-icon :icon="checkmarkCircleOutline" />
          Confirmed
        </span>
        <h4 ref="titleRef" tabindex="-1">{{ props.title }}</h4>
        <p v-if="bookingDialogContent.confirmation.subtitle" class="booking-confirmation__subtitle">
          {{ bookingDialogContent.confirmation.subtitle }}
        </p>
      </div>

      <div class="booking-confirmation__details">
        <div class="booking-confirmation__card">
          <div class="booking-confirmation__card-header">
            <ion-icon :icon="receiptOutline" />
            <span>{{ bookingDialogContent.confirmation.bookingIdLabel }}</span>
          </div>
          <p class="booking-confirmation__card-value">{{ props.bookingIdText }}</p>
        </div>

        <div class="booking-confirmation__card">
          <div class="booking-confirmation__card-header">
            <ion-icon :icon="calendarOutline" />
            <span>{{ bookingDialogContent.confirmation.stayLabel }}</span>
          </div>
          <p class="booking-confirmation__card-value">{{ props.dateRangeLabel }}</p>
        </div>
      </div>

      <div class="booking-confirmation__next-step">
        <p class="booking-confirmation__next-step-label">
          {{ bookingDialogContent.confirmation.nextStepLabel }}
        </p>
        <p class="booking-confirmation__next-step-text">
          {{ bookingDialogContent.confirmation.nextStepText }}
        </p>
      </div>
    </div>
    <div class="booking-dialog__actions">
      <ion-button @click="handleDone">{{ props.doneLabel }}</ion-button>
    </div>
  </div>
</template>

<style scoped src="./booking-dialog.shared.css"></style>

<style scoped>
.booking-confirmation {
  display: grid;
  gap: 16px;
  padding: 22px 18px;
  border-radius: 18px;
  background: linear-gradient(180deg, #fffaf3 0%, #f7efe2 100%);
  border: 1px solid rgba(31, 27, 24, 0.08);
  box-shadow: 0 14px 28px rgba(84, 61, 42, 0.08);
}

.booking-confirmation__hero {
  display: grid;
  gap: 8px;
  text-align: center;
}

.booking-confirmation__badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  width: fit-content;
  margin: 0 auto;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(60, 129, 90, 0.14);
  color: #2c6b4b;
  font-size: 0.8rem;
  font-weight: 700;
  letter-spacing: 0.06em;
  text-transform: uppercase;
}

.booking-confirmation__hero h4 {
  margin: 0;
  font-size: 1.35rem;
}

.booking-confirmation__subtitle {
  margin: 0 auto;
  max-width: 34ch;
  color: var(--color-olive);
  line-height: 1.6;
}

.booking-confirmation__details {
  display: grid;
  gap: 12px;
}

.booking-confirmation__card {
  display: grid;
  gap: 8px;
  padding: 14px 16px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.booking-confirmation__card-header {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--color-olive);
  font-size: 0.82rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.booking-confirmation__card-value {
  margin: 0;
  color: var(--color-midnight);
  font-size: 1rem;
  font-weight: 600;
}

.booking-confirmation__next-step {
  padding: 14px 16px;
  border-radius: 14px;
  background: rgba(124, 84, 65, 0.08);
  display: grid;
  gap: 4px;
}

.booking-confirmation__next-step-label {
  margin: 0;
  color: var(--color-olive);
  font-size: 0.78rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.booking-confirmation__next-step-text {
  margin: 0;
  color: var(--color-midnight);
  line-height: 1.5;
}

.booking-dialog__actions {
  padding-top: 2px;
}

@media (min-width: 720px) {
  .booking-confirmation__details {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 640px) {
  .booking-confirmation {
    padding-bottom: calc(92px + env(safe-area-inset-bottom, 0px));
  }

  .booking-dialog__actions {
    position: fixed;
    left: 16px;
    right: 16px;
    bottom: calc(10px + env(safe-area-inset-bottom, 0px));
    z-index: 80;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-end;
    padding-top: 0;
    background: transparent;
    backdrop-filter: none;
    border: none;
    border-radius: 0;
    box-shadow: none;
  }

  .booking-dialog__actions ion-button {
    margin: 0;
    min-width: 0;
    width: 100%;
    min-height: 46px;
  }
}

</style>
