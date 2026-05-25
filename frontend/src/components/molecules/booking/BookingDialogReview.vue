<script setup lang="ts">
import { nextTick, onMounted, ref } from "vue"
import { IonButton } from "@ionic/vue"
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

<style scoped src="./booking-dialog.shared.css"></style>

<style scoped>
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

@media (max-width: 640px) {
  .booking-review__row {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
