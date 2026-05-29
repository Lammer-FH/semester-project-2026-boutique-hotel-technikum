<script setup lang="ts">
import { nextTick, onMounted, ref } from "vue"
import { IonButton, IonIcon } from "@ionic/vue"
import { eyeOutline, swapHorizontalOutline } from "ionicons/icons"
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
  guestCountOptions: number[]
  roomMaxGuests: number
  bookingMessage: string
}>()

const emit = defineEmits<{
  (event: "update-field", key: keyof BookingRequest, value: BookingRequest[keyof BookingRequest]): void
  (event: "proceed"): void
  (event: "change-dates"): void
}>()

const updateField = <K extends keyof BookingRequest>(
  key: K,
  value: BookingRequest[K]
) => {
  emit("update-field", key, value)
}

const firstFieldRef = ref<HTMLInputElement | null>(null)

const handleProceed = () => {
  emit("proceed")
}

onMounted(async () => {
  await nextTick()
  firstFieldRef.value?.focus()
})
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
          ref="firstFieldRef"
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
      <ion-button fill="clear" @click="$emit('change-dates')">
        <template v-slot:start>
<ion-icon :icon="swapHorizontalOutline"  />
</template>
        {{ bookingDialogContent.buttons.changeDates }}
      </ion-button>
      <ion-button @click="handleProceed">
        <template v-slot:start>
<ion-icon :icon="eyeOutline"  />
</template>
        {{ bookingDialogContent.buttons.review }}
      </ion-button>
    </div>
  </div>
</template>

<style scoped src="./booking-dialog.shared.css"></style>

<style scoped>
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

@media (min-width: 720px) {
  .booking-dialog__grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

/* Reduce spacing between action buttons in the details form */
.booking-dialog__actions {
  gap: 6px;
}

@media (max-width: 640px) {
  .booking-dialog__actions {
    gap: 8px;
  }
}
</style>
