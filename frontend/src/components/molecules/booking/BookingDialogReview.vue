<script setup lang="ts">
import { IonButton } from "@ionic/vue"
import { bookingDialogContent } from "@/data/hotelContent"
import type { BookingRequest } from "@/core/models/booking"

type StepContent = {
  label: string
  title: string
  hint: string
}

const props = defineProps<{
  stepContent: StepContent
  draft: Partial<BookingRequest>
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
</script>

<template>
  <div class="booking-dialog__section">
    <div class="booking-dialog__intro">
      <p class="booking-dialog__step">{{ props.stepContent.label }}</p>
      <h4 class="booking-dialog__step-title">{{ props.stepContent.title }}</h4>
      <p class="booking-dialog__hint">{{ props.stepContent.hint }}</p>
    </div>
    <div class="booking-review">
      <div class="booking-review__row">
        <span>{{ bookingDialogContent.reviewLabels.leadGuest }}</span>
        <strong>{{ props.draft.guestFirstName }} {{ props.draft.guestLastName }}</strong>
      </div>
      <div class="booking-review__row">
        <span>{{ bookingDialogContent.reviewLabels.email }}</span>
        <strong>{{ props.draft.guestEmail }}</strong>
      </div>
      <div class="booking-review__row">
        <span>{{ bookingDialogContent.reviewLabels.guests }}</span>
        <strong>{{ props.draft.guestCount ?? 1 }}</strong>
      </div>
      <div class="booking-review__row">
        <span>{{ bookingDialogContent.reviewLabels.breakfast }}</span>
        <strong>
          {{
            props.draft.breakfastIncluded
              ? bookingDialogContent.reviewLabels.included
              : bookingDialogContent.reviewLabels.notIncluded
          }}
        </strong>
      </div>
    </div>

    <p v-if="props.bookingMessage" class="booking-dialog__error" aria-live="polite">
      {{ props.bookingMessage }}
    </p>

    <div class="booking-dialog__actions">
      <ion-button fill="outline" @click="handleBack">
        {{ bookingDialogContent.buttons.back }}
      </ion-button>
      <ion-button :disabled="props.isSubmitting" @click="handleSubmit">
        {{
          props.isSubmitting
            ? bookingDialogContent.buttons.confirming
            : bookingDialogContent.buttons.confirm
        }}
      </ion-button>
    </div>
  </div>
</template>

<style scoped>
.booking-dialog__section {
  display: grid;
  gap: 16px;
}

.booking-dialog__intro {
  display: grid;
  gap: 6px;
}

.booking-dialog__step {
  margin: 0;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
  font-weight: 600;
}

.booking-dialog__step-title {
  margin: 0;
  font-size: 1.05rem;
}

.booking-dialog__hint {
  margin: 0;
  color: var(--color-ink);
  font-size: 0.95rem;
}

.booking-review {
  background: var(--color-cream);
  border-radius: 16px;
  padding: 14px 16px;
  display: grid;
  gap: 10px;
}

.booking-review__row {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  font-size: 0.95rem;
}

.booking-review__row strong {
  color: var(--color-midnight);
}

.booking-dialog__actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.booking-dialog__error {
  margin: 0;
  color: var(--color-terracotta);
  font-weight: 600;
}

@media (max-width: 640px) {
  .booking-review__row {
    flex-direction: column;
    align-items: flex-start;
  }

  .booking-dialog__actions {
    flex-direction: column;
    align-items: stretch;
  }

  .booking-dialog__actions ion-button {
    width: 100%;
  }
}
</style>
