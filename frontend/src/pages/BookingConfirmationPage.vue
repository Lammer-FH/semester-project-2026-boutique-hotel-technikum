<script setup lang="ts">
import { computed, onMounted, watch } from "vue"
import { IonButton } from "@ionic/vue"
import { useRoute, useRouter } from "vue-router"
import { storeToRefs } from "pinia"
import BaseErrorBanner from "@/components/atoms/BaseErrorBanner.vue"
import PageLayout from "@/components/layout/PageLayout.vue"
import BookingDialogConfirmation from "@/components/molecules/booking/BookingDialogConfirmation.vue"
import { useBookingStore } from "@/application/stores/bookingStore"
import { bookingDialogContent } from "@/data/content/bookingContent"

const route = useRoute()
const router = useRouter()
const bookingStore = useBookingStore()
const { currentBooking, isLoading, error } = storeToRefs(bookingStore)

const bookingId = computed(() => Number(route.params.bookingId))
const hasValidBookingId = computed(() => Number.isInteger(bookingId.value) && bookingId.value > 0)
const shouldFetchBooking = computed(
  () => hasValidBookingId.value && currentBooking.value?.bookingId !== bookingId.value
)

const loadBooking = async () => {
  if (!hasValidBookingId.value) {
    return
  }

  if (shouldFetchBooking.value) {
    await bookingStore.getBooking(bookingId.value)
  }
}

const goToRooms = () => {
  void router.push({ name: "RoomsIndex" })
}

onMounted(loadBooking)

watch(
  () => route.params.bookingId,
  () => {
    void loadBooking()
  }
)

watch(
  () => currentBooking.value?.bookingId,
  (loadedBookingId) => {
    if (!loadedBookingId && hasValidBookingId.value && !isLoading.value) {
      void loadBooking()
    }
  }
)
</script>

<template>
  <page-layout
    content-class="page-shell"
    layout-class="booking-confirmation-page__layout"
    inner-class="page-shell__inner booking-confirmation-page"
    footer-class="booking-confirmation-page__footer"
  >
    <div class="booking-confirmation-page__intro ion-margin-top">
      <p class="booking-confirmation-page__eyebrow">
        {{ bookingDialogContent.confirmation.pageEyebrow }}
      </p>
      <h1>{{ bookingDialogContent.confirmation.title }}</h1>
    </div>

    <base-error-banner
      v-if="!hasValidBookingId"
      :message="bookingDialogContent.confirmation.invalidBookingMessage"
    />

    <base-error-banner
      v-else-if="error"
      :message="bookingDialogContent.confirmation.invalidBookingMessage"
      :detail="error"
    />

    <div v-else-if="isLoading" class="booking-confirmation-page__loading">
      {{ bookingDialogContent.confirmation.loadingText }}
    </div>

    <booking-dialog-confirmation
      v-else-if="currentBooking"
      page-mode
      :title="bookingDialogContent.confirmation.overviewTitle"
      :booking="currentBooking"
      :done-label="bookingDialogContent.buttons.backToRooms"
      @done="goToRooms"
    />

    <div v-else class="booking-confirmation-page__empty">
      <p>{{ bookingDialogContent.confirmation.emptyText }}</p>
      <ion-button @click="goToRooms">{{ bookingDialogContent.buttons.backToRooms }}</ion-button>
    </div>
  </page-layout>
</template>

<style scoped>
.booking-confirmation-page {
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 18px;
  min-height: 100%;
  padding-bottom: 32px;
}

.booking-confirmation-page__layout {
  display: flex;
  flex: 1;
  flex-direction: column;
  min-height: 100%;
}

.booking-confirmation-page__footer {
  margin-top: auto;
}

.booking-confirmation-page__intro {
  display: grid;
  gap: 6px;
}

.booking-confirmation-page__intro h1 {
  margin: 0;
  font-size: clamp(1.8rem, 4vw, 2.5rem);
}

.booking-confirmation-page__eyebrow {
  margin: 0;
  color: var(--color-olive);
  font-size: var(--text-label-sm);
  font-weight: 700;
  letter-spacing: var(--tracking-medium);
  text-transform: uppercase;
}

.booking-confirmation-page__loading,
.booking-confirmation-page__empty {
  padding: 18px;
  border-radius: 8px;
  background: var(--color-cream);
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.booking-confirmation-page__empty {
  display: grid;
  gap: 12px;
  justify-items: start;
}

.booking-confirmation-page__empty p {
  margin: 0;
}

@media print {
  :global(@page) {
    size: A4;
    margin: 12mm 10mm;
  }

  .booking-confirmation-page__intro,
  .booking-confirmation-page__footer {
    display: none !important;
  }

  .booking-confirmation-page {
    display: block;
    min-height: auto;
    padding: 0;
  }
}
</style>
