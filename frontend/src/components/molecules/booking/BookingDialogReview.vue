<script setup lang="ts">
import { nextTick, onMounted, ref } from "vue"
import { IonButton, IonIcon } from "@ionic/vue"
import {
  arrowBackOutline,
  checkmarkOutline,
  mailOutline,
  peopleOutline,
  personOutline,
} from "ionicons/icons"
import { bookingDialogContent } from "@/data/content/bookingContent"
import type { BookingRequest } from "@/core/models/booking"

type StepContent = {
  label: string
  title: string
  hint: string
}

const props = defineProps<{
  stepContent: StepContent
  draft: Partial<BookingRequest>
  dateRangeLabel: string
  bookingMessage: string
  isSubmitting: boolean
}>()

const emit = defineEmits<{
  (event: "back"): void
  (event: "submit"): void
}>()

const handleBack = () => {
  emit("back")
}

const handleSubmit = () => {
  emit("submit")
}

const titleRef = ref<HTMLHeadingElement | null>(null)

onMounted(async () => {
  await nextTick()
  titleRef.value?.focus()
})
</script>

<template>
  <div class="booking-dialog__section">
    <div class="booking-dialog__intro">
      <p class="booking-dialog__step">{{ props.stepContent.label }}</p>
      <h4 class="booking-dialog__step-title" ref="titleRef" tabindex="-1">
        {{ props.stepContent.title }}
      </h4>
      <p class="booking-dialog__hint">{{ props.stepContent.hint }}</p>
      <p class="booking-review__note">{{ bookingDialogContent.reviewSummary.note }}</p>
    </div>
    <div class="booking-review">
      <div class="booking-review__layout">
        <article class="booking-review__card booking-review__card--combined">
          <div class="booking-review__card-header">
            <ion-icon :icon="personOutline" />
            <span>{{ bookingDialogContent.reviewSummary.summarySection }}</span>
          </div>

          <div class="booking-review__combined-grid">
            <div class="booking-review__guest">
              <p class="booking-review__name">
                {{ props.draft.guestFirstName }} {{ props.draft.guestLastName }}
              </p>
              <div class="booking-review__meta-row">
                <ion-icon :icon="mailOutline" />
                <span>{{ props.draft.guestEmail }}</span>
              </div>
            </div>

            <div class="booking-review__stay">
              <p class="booking-review__value">{{ props.dateRangeLabel }}</p>
              <div class="booking-review__detail-list">
                <div class="booking-review__detail-item">
                  <div class="booking-review__detail-label">
                    <ion-icon :icon="peopleOutline" />
                    <span>{{ bookingDialogContent.reviewLabels.guests }}</span>
                  </div>
                  <strong>{{ props.draft.guestCount ?? 1 }}</strong>
                </div>
                <div class="booking-review__detail-item">
                  <div class="booking-review__detail-label">
                    <span>{{ bookingDialogContent.reviewLabels.breakfast }}</span>
                  </div>
                  <strong>
                    {{
                      props.draft.breakfastIncluded
                        ? bookingDialogContent.reviewLabels.included
                        : bookingDialogContent.reviewLabels.notIncluded
                    }}
                  </strong>
                </div>
              </div>
            </div>
          </div>
        </article>
      </div>
    </div>

    <p
      v-if="props.bookingMessage"
      class="booking-dialog__error"
      role="alert"
      aria-live="assertive"
      aria-atomic="true"
    >
      {{ props.bookingMessage }}
    </p>

    <div class="booking-dialog__actions">
      <ion-button fill="outline" @click="handleBack">
        <template v-slot:start>
          <ion-icon :icon="arrowBackOutline" />
        </template>
        {{ bookingDialogContent.buttons.back }}
      </ion-button>
      <ion-button :disabled="props.isSubmitting" @click="handleSubmit">
        <template v-slot:start>
          <ion-icon :icon="checkmarkOutline" />
        </template>
        {{
          props.isSubmitting
            ? bookingDialogContent.buttons.confirming
            : bookingDialogContent.buttons.confirm
        }}
      </ion-button>
    </div>
  </div>
</template>

<style scoped src="./booking-dialog.shared.css"></style>

<style scoped>
.booking-review {
  background: linear-gradient(180deg, rgba(255, 250, 243, 0.98), rgba(247, 239, 226, 0.94));
  border: 1px solid rgba(31, 27, 24, 0.08);
  border-radius: 18px;
  padding: 18px;
  box-shadow: 0 14px 28px rgba(84, 61, 42, 0.08);
}

.booking-review__note {
  margin: 0;
  color: var(--color-olive);
  font-size: 0.92rem;
}

.booking-review__layout {
  display: grid;
  gap: 14px;
}

.booking-review__card--combined {
  padding: 14px;
}

.booking-review__combined-grid {
  display: grid;
  gap: 12px;
}

.booking-review__guest {
  display: grid;
  gap: 6px;
}

.booking-review__stay {
  display: grid;
  gap: 6px;
}

.booking-review__card {
  display: grid;
  gap: 12px;
  padding: 16px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.booking-review__card--stay {
  padding: 20px;
  background: linear-gradient(180deg, rgba(255, 247, 239, 0.98), rgba(247, 232, 218, 0.96));
  border-color: rgba(161, 79, 54, 0.14);
}

.booking-review__card-header {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--color-olive);
  font-size: 0.8rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.booking-review__name,
.booking-review__value {
  margin: 0;
  color: var(--color-midnight);
  font-size: 1rem;
  font-weight: 600;
  line-height: 1.5;
}

.booking-review__meta-row {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--color-olive);
  word-break: break-word;
}

.booking-review__detail-list {
  display: grid;
  gap: 10px;
}

.booking-review__detail-item {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.booking-review__detail-label {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--color-olive);
}

.booking-review__detail-item strong {
  color: var(--color-midnight);
}

@media (min-width: 720px) {
  .booking-review__layout {
    grid-template-columns: 1fr;
    align-items: start;
    gap: 18px;
  }

  .booking-review__card--stay {
    padding: 22px;
  }
  .booking-review__combined-grid {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 640px) {
  .booking-review {
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
