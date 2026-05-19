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
  guestCountOptions: number[]
  roomMaxGuests: number
  bookingMessage: string
}>()

const emit = defineEmits<{
  (event: "update-field", key: keyof BookingRequest, value: BookingRequest[keyof BookingRequest]): void
  (event: "proceed"): void
}>()

const updateField = <K extends keyof BookingRequest>(
  key: K,
  value: BookingRequest[K]
) => {
  emit("update-field", key, value)
}

const handleProceed = () => {
  emit("proceed")
}
</script>

<template>
  <div class="booking-dialog__section">
    <div class="booking-dialog__intro">
      <p class="booking-dialog__step">{{ props.stepContent.label }}</p>
      <h4 class="booking-dialog__step-title">{{ props.stepContent.title }}</h4>
      <p class="booking-dialog__hint">{{ props.stepContent.hint }}</p>
    </div>
    <div class="booking-dialog__grid">
      <div class="booking-field">
        <label class="booking-field__label" for="booking-first-name">
          {{ bookingDialogContent.fields.firstName }}
        </label>
        <input
          id="booking-first-name"
          class="booking-field__input"
          type="text"
          autocomplete="given-name"
          :placeholder="bookingDialogContent.placeholders.firstName"
          required
          :value="props.draft.guestFirstName ?? ''"
          @input="updateField('guestFirstName', ($event.target as HTMLInputElement).value)"
        />
      </div>
      <div class="booking-field">
        <label class="booking-field__label" for="booking-last-name">
          {{ bookingDialogContent.fields.lastName }}
        </label>
        <input
          id="booking-last-name"
          class="booking-field__input"
          type="text"
          autocomplete="family-name"
          :placeholder="bookingDialogContent.placeholders.lastName"
          required
          :value="props.draft.guestLastName ?? ''"
          @input="updateField('guestLastName', ($event.target as HTMLInputElement).value)"
        />
      </div>
      <div class="booking-field">
        <label class="booking-field__label" for="booking-email">
          {{ bookingDialogContent.fields.email }}
        </label>
        <input
          id="booking-email"
          class="booking-field__input"
          type="email"
          inputmode="email"
          autocomplete="email"
          :placeholder="bookingDialogContent.placeholders.email"
          required
          :value="props.draft.guestEmail ?? ''"
          @input="updateField('guestEmail', ($event.target as HTMLInputElement).value)"
        />
      </div>
      <div class="booking-field">
        <label class="booking-field__label" for="booking-email-confirm">
          {{ bookingDialogContent.fields.confirmEmail }}
        </label>
        <input
          id="booking-email-confirm"
          class="booking-field__input"
          type="email"
          inputmode="email"
          autocomplete="email"
          :placeholder="bookingDialogContent.placeholders.confirmEmail"
          required
          :value="props.draft.confirmEmail ?? ''"
          @input="updateField('confirmEmail', ($event.target as HTMLInputElement).value)"
        />
      </div>
      <div class="booking-field">
        <label class="booking-field__label" for="booking-guests">
          {{ bookingDialogContent.fields.guests }}
        </label>
        <select
          id="booking-guests"
          class="booking-field__select"
          required
          :value="props.draft.guestCount ?? 1"
          @change="updateField('guestCount', Number(($event.target as HTMLSelectElement).value))"
        >
          <option v-for="count in props.guestCountOptions" :key="count" :value="count">
            {{ count }}
          </option>
        </select>
        <p class="booking-field__helper">
          {{ bookingDialogContent.helpers.maxGuests(props.roomMaxGuests) }}
        </p>
      </div>
      <div class="booking-field booking-field--toggle">
        <label class="booking-field__label" for="booking-breakfast">
          {{ bookingDialogContent.fields.breakfast }}
        </label>
        <input
          id="booking-breakfast"
          class="booking-field__checkbox"
          type="checkbox"
          :checked="props.draft.breakfastIncluded ?? false"
          @change="updateField('breakfastIncluded', ($event.target as HTMLInputElement).checked)"
        />
      </div>
    </div>

    <p v-if="props.bookingMessage" class="booking-dialog__error" aria-live="polite">
      {{ props.bookingMessage }}
    </p>

    <div class="booking-dialog__actions">
      <ion-button @click="handleProceed">{{ bookingDialogContent.buttons.review }}</ion-button>
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

.booking-dialog__grid {
  display: grid;
  gap: 14px;
}

.booking-field {
  display: grid;
  gap: 6px;
}

.booking-field__label {
  font-weight: 600;
  color: var(--color-midnight);
}

.booking-field__input,
.booking-field__select {
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid rgba(31, 27, 24, 0.15);
  background: #fff;
  font-size: 0.95rem;
}

.booking-field__helper {
  margin: 0;
  font-size: 0.85rem;
  color: var(--color-olive);
}

.booking-field--toggle {
  grid-template-columns: 1fr auto;
  align-items: center;
}

.booking-field__checkbox {
  width: 18px;
  height: 18px;
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
  .booking-dialog__actions {
    flex-direction: column;
    align-items: stretch;
  }

  .booking-dialog__actions ion-button {
    width: 100%;
  }
}

@media (min-width: 720px) {
  .booking-dialog__grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>
