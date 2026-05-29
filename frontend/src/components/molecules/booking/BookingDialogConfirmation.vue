<script setup lang="ts">
import { computed, nextTick, onMounted, ref } from "vue"
import { IonButton, IonIcon } from "@ionic/vue"
import {
  bedOutline,
  calendarOutline,
  callOutline,
  carOutline,
  cashOutline,
  checkmarkCircleOutline,
  locationOutline,
  mailOutline,
  mapOutline,
  peopleOutline,
  personOutline,
  printOutline,
  receiptOutline,
  trainOutline,
} from "ionicons/icons"
import { bookingDialogContent } from "@/data/content/bookingContent"
import { formatDate } from "@/core/dateutils"
import { formatPrice } from "@/core/formatters"
import { buildOsmTileGrid, OSM_ATTRIBUTION_TEXT, OSM_ATTRIBUTION_URL } from "@/core/osmMap"
import { resolveExtraIcon } from "@/core/extraIcons"
import type { BookingConfirmation } from "@/core/models/booking"

const props = withDefaults(
  defineProps<{
    title: string
    booking: BookingConfirmation
    doneLabel?: string
    pageMode?: boolean
  }>(),
  {
    doneLabel: "",
    pageMode: false,
  }
)

const emit = defineEmits<{
  (event: "done"): void
}>()

const isPrinting = ref(false)
const handleDone = () => {
  emit("done")
}

const dateRangeLabel = computed(() =>
  `${formatDate(props.booking.checkInDate)} - ${formatDate(props.booking.checkOutDate)}`
)

const primaryImage = computed(() => {
  if (!props.booking.room.images.length) {
    return {
      url: `/images/rooms/${props.booking.room.id}/main.jpg`,
      altText: `${props.booking.room.title} room interior`,
    }
  }

  const sorted = [...props.booking.room.images].sort((a, b) => a.sortOrder - b.sortOrder)
  return sorted[0]
})

const addressLines = computed(() => [
  props.booking.hotelContact.street,
  `${props.booking.hotelContact.postalCode} ${props.booking.hotelContact.city}`,
  props.booking.hotelContact.country,
])

const mapTiles = computed(() =>
  buildOsmTileGrid(
    props.booking.hotelContact.latitude,
    props.booking.hotelContact.longitude,
    16,
    4,
    2
  )
)

const breakfastLabel = computed(() =>
  props.booking.breakfastIncluded
    ? bookingDialogContent.reviewLabels.included
    : bookingDialogContent.reviewLabels.notIncluded
)

const preloadMapImage = () =>
  Promise.all(
    mapTiles.value.map(
      (tile) =>
        new Promise<void>((resolve) => {
          const image = new Image()
          image.onload = () => resolve()
          image.onerror = () => resolve()
          image.src = tile.url
        })
    )
  ).then(() => undefined)

const handlePrint = async () => {
  isPrinting.value = true
  await preloadMapImage()
  window.print()
  isPrinting.value = false
}

const titleRef = ref<HTMLHeadingElement | null>(null)

onMounted(async () => {
  await nextTick()
  titleRef.value?.focus()
})
</script>

<template>
  <div
    class="booking-dialog__section booking-confirmation-shell"
    :class="{ 'booking-confirmation-shell--page': props.pageMode }"
  >
    <div class="booking-confirmation booking-confirmation-print">
      <div class="booking-confirmation__hero">
        <span class="booking-confirmation__badge">
          <ion-icon :icon="checkmarkCircleOutline" />
          {{ bookingDialogContent.confirmation.statusLabel }}
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
          <p class="booking-confirmation__card-value">
            {{ bookingDialogContent.confirmation.bookingId(props.booking.bookingId) }}
          </p>
        </div>

        <div class="booking-confirmation__card">
          <div class="booking-confirmation__card-header">
            <ion-icon :icon="calendarOutline" />
            <span>{{ bookingDialogContent.confirmation.stayLabel }}</span>
          </div>
          <p class="booking-confirmation__card-value">{{ dateRangeLabel }}</p>
          <p class="booking-confirmation__card-note">
            {{ bookingDialogContent.confirmation.nights(props.booking.priceBreakdown.nights) }}
          </p>
        </div>

        <div class="booking-confirmation__card">
          <div class="booking-confirmation__card-header">
            <ion-icon :icon="peopleOutline" />
            <span>{{ bookingDialogContent.confirmation.guestsLabel }}</span>
          </div>
          <p class="booking-confirmation__card-value">{{ props.booking.guestCount }}</p>
        </div>

        <div class="booking-confirmation__card">
          <div class="booking-confirmation__card-header">
            <ion-icon :icon="cashOutline" />
            <span>{{ bookingDialogContent.confirmation.totalLabel }}</span>
          </div>
          <p class="booking-confirmation__card-value">
            {{ formatPrice(props.booking.totalPrice) }} EUR
          </p>
        </div>
      </div>

      <section class="booking-confirmation__room" :aria-label="bookingDialogContent.confirmation.roomSection">
        <img
          class="booking-confirmation__room-image"
          :src="primaryImage.url"
          :alt="primaryImage.altText"
        />
        <div class="booking-confirmation__room-body">
          <div class="booking-confirmation__section-heading">
            <ion-icon :icon="bedOutline" />
            <span>{{ bookingDialogContent.confirmation.roomSection }}</span>
          </div>
          <h5>{{ props.booking.room.title }}</h5>
          <p>{{ props.booking.room.description }}</p>
          <div class="booking-confirmation__extras">
            <span
              v-for="extra in props.booking.room.extras"
              :key="extra.id"
              class="booking-confirmation__extra"
            >
              <ion-icon :icon="resolveExtraIcon(extra.iconName)" />
              {{ extra.title }}
            </span>
          </div>
        </div>
      </section>

      <div class="booking-confirmation__info-grid">
        <section class="booking-confirmation__panel">
          <div class="booking-confirmation__section-heading">
            <ion-icon :icon="personOutline" />
            <span>{{ bookingDialogContent.confirmation.guestSection }}</span>
          </div>
          <p class="booking-confirmation__strong">
            {{ props.booking.guest.firstName }} {{ props.booking.guest.lastName }}
          </p>
          <p class="booking-confirmation__link-row">
            <ion-icon :icon="mailOutline" />
            <a :href="`mailto:${props.booking.guest.email}`">{{ props.booking.guest.email }}</a>
          </p>
          <p class="booking-confirmation__line">
            {{ bookingDialogContent.confirmation.breakfastLabel }}: {{ breakfastLabel }}
          </p>
        </section>

        <section class="booking-confirmation__panel">
          <div class="booking-confirmation__section-heading">
            <ion-icon :icon="callOutline" />
            <span>{{ bookingDialogContent.confirmation.contactSection }}</span>
          </div>
          <p class="booking-confirmation__strong">{{ props.booking.hotelContact.name }}</p>
          <p class="booking-confirmation__line" v-for="line in addressLines" :key="line">
            {{ line }}
          </p>
          <p class="booking-confirmation__link-row">
            <ion-icon :icon="mailOutline" />
            <a :href="`mailto:${props.booking.hotelContact.email}`">
              {{ props.booking.hotelContact.email }}
            </a>
          </p>
          <p class="booking-confirmation__link-row">
            <ion-icon :icon="callOutline" />
            <a :href="`tel:${props.booking.hotelContact.phone}`">
              {{ props.booking.hotelContact.phone }}
            </a>
          </p>
        </section>
      </div>

      <section class="booking-confirmation__panel booking-confirmation__directions">
        <div class="booking-confirmation__section-heading">
          <ion-icon :icon="locationOutline" />
          <span>{{ bookingDialogContent.confirmation.directionsSection }}</span>
        </div>
        <div class="booking-confirmation__direction-list">
          <p>
            <ion-icon :icon="trainOutline" />
            <span>{{ props.booking.directions.byTrain }}</span>
          </p>
          <p>
            <ion-icon :icon="carOutline" />
            <span>{{ props.booking.directions.byCar }}</span>
          </p>
          <p>
            <ion-icon :icon="carOutline" />
            <span>{{ props.booking.directions.parking }}</span>
          </p>
        </div>
      </section>

      <section class="booking-confirmation__map" :aria-label="bookingDialogContent.confirmation.mapSection">
        <div class="booking-confirmation__section-heading">
          <ion-icon :icon="mapOutline" />
          <span>{{ bookingDialogContent.confirmation.mapSection }}</span>
        </div>
        <div class="booking-confirmation__map-image" role="img" :aria-label="bookingDialogContent.confirmation.mapAlt">
          <img
            v-for="tile in mapTiles"
            :key="tile.key"
            :src="tile.url"
            alt=""
            loading="eager"
          />
          <span class="booking-confirmation__map-marker" aria-hidden="true"></span>
        </div>
        <a class="booking-confirmation__attribution" :href="OSM_ATTRIBUTION_URL" target="_blank" rel="noreferrer">
          {{ OSM_ATTRIBUTION_TEXT }}
        </a>
      </section>

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
      <ion-button fill="outline" :disabled="isPrinting" @click="handlePrint">
        <template v-slot:start>
          <ion-icon :icon="printOutline" />
        </template>
        {{ bookingDialogContent.buttons.print }}
      </ion-button>
      <ion-button v-if="props.doneLabel" @click="handleDone">{{ props.doneLabel }}</ion-button>
    </div>
  </div>
</template>

<style scoped src="./booking-dialog.shared.css"></style>

<style scoped>
.booking-confirmation {
  display: grid;
  gap: 14px;
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

.booking-confirmation__card-note,
.booking-confirmation__line {
  margin: 0;
  color: var(--color-olive);
  line-height: 1.4;
}

.booking-confirmation__room {
  display: grid;
  gap: 14px;
  padding: 14px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.booking-confirmation__room-image {
  width: 100%;
  aspect-ratio: 16 / 9;
  object-fit: cover;
  border-radius: 10px;
  background: var(--color-mist);
}

.booking-confirmation__room-body,
.booking-confirmation__panel,
.booking-confirmation__directions,
.booking-confirmation__map {
  display: grid;
  gap: 10px;
}

.booking-confirmation__room-body h5 {
  margin: 0;
  color: var(--color-midnight);
  font-size: 1.1rem;
}

.booking-confirmation__room-body p {
  margin: 0;
}

.booking-confirmation__section-heading {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--color-olive);
  font-size: 0.78rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.booking-confirmation__extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.booking-confirmation__extra {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 7px 10px;
  border-radius: 999px;
  background: rgba(161, 79, 54, 0.1);
  color: var(--color-midnight);
  font-weight: 600;
  font-size: 0.9rem;
}

.booking-confirmation__extra ion-icon {
  color: var(--color-terracotta);
}

.booking-confirmation__info-grid {
  display: grid;
  gap: 12px;
}

.booking-confirmation__panel {
  padding: 14px 16px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.booking-confirmation__strong {
  margin: 0;
  color: var(--color-midnight);
  font-weight: 700;
}

.booking-confirmation__link-row {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  margin: 0;
  word-break: break-word;
}

.booking-confirmation__direction-list {
  display: grid;
  gap: 8px;
}

.booking-confirmation__direction-list p {
  display: grid;
  grid-template-columns: 20px 1fr;
  gap: 8px;
  margin: 0;
  line-height: 1.45;
}

.booking-confirmation__direction-list ion-icon {
  margin-top: 2px;
  color: var(--color-terracotta);
}

.booking-confirmation__map {
  padding: 14px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.booking-confirmation__map-image {
  position: relative;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  grid-template-rows: repeat(2, minmax(0, 1fr));
  width: 100%;
  aspect-ratio: 2 / 1;
  overflow: hidden;
  border-radius: 10px;
  border: 1px solid rgba(31, 27, 24, 0.08);
  background: var(--color-mist);
}

.booking-confirmation__map-image img {
  width: 100%;
  height: 100%;
  display: block;
}

.booking-confirmation__map-marker {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 20px;
  height: 20px;
  border-radius: 50% 50% 50% 0;
  background: var(--color-terracotta);
  border: 2px solid #fff;
  box-shadow: 0 3px 10px rgba(31, 27, 24, 0.35);
  transform: translate(-50%, -92%) rotate(-45deg);
}

.booking-confirmation__map-marker::after {
  content: "";
  position: absolute;
  inset: 5px;
  border-radius: 50%;
  background: #fff;
}

.booking-confirmation__attribution {
  justify-self: end;
  margin-top: -2px;
  font-size: 0.72rem;
  color: var(--color-olive);
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

.booking-confirmation-shell--page {
  gap: 20px;
}

.booking-confirmation-shell--page .booking-confirmation {
  border-radius: 8px;
}

.booking-confirmation-shell--page .booking-dialog__actions {
  justify-content: center;
  padding-bottom: 16px;
}

@media (min-width: 720px) {
  .booking-confirmation__details {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }

  .booking-confirmation__room {
    grid-template-columns: minmax(190px, 0.8fr) 1.2fr;
    align-items: start;
  }

  .booking-confirmation__info-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 640px) {
  .booking-confirmation-shell:not(.booking-confirmation-shell--page) .booking-confirmation {
    padding-bottom: calc(150px + env(safe-area-inset-bottom, 0px));
  }

  .booking-confirmation-shell:not(.booking-confirmation-shell--page) .booking-dialog__actions {
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

  .booking-confirmation-shell:not(.booking-confirmation-shell--page) .booking-dialog__actions ion-button {
    margin: 0;
    min-width: 0;
    width: 100%;
    min-height: 46px;
  }

  .booking-confirmation-shell--page .booking-dialog__actions {
    flex-direction: column;
    align-items: stretch;
  }
}

@media print {
  :global(body) {
    background: #fff !important;
  }

  :global(ion-header),
  :global(.app-header),
  :global(.app-footer),
  :global(the-header),
  :global(the-footer) {
    display: none !important;
  }

  :global(body *:not(.booking-confirmation-print):not(.booking-confirmation-print *):not(.booking-confirmation-shell):not(.booking-confirmation-shell *):not(ion-page):not(ion-page *):not(ion-content):not(ion-content *)) {
    visibility: hidden !important;
  }

  :global(ion-modal.dialog-modal) {
    --width: 100vw;
    --height: auto;
    --max-height: none;
    position: static !important;
    visibility: visible !important;
  }

  :global(ion-content.dialog-shell) {
    --background: #fff;
    --padding-start: 0;
    --padding-end: 0;
    --padding-top: 0;
    --padding-bottom: 0;
    visibility: visible !important;
  }

  :global(ion-content.dialog-shell::part(scroll)) {
    overflow: visible !important;
    padding: 0 !important;
  }

  :global(ion-content.dialog-shell > *:not(.booking-dialog__section)) {
    display: none !important;
  }

  .booking-dialog__actions {
    display: none !important;
  }

  .booking-confirmation-print,
  .booking-confirmation-print * {
    visibility: visible !important;
  }

  .booking-confirmation {
    width: 190mm;
    margin: 8mm auto 0;
    padding: 0;
    gap: 7px;
    border: 0;
    border-radius: 0;
    box-shadow: none;
    background: #fff;
    color: #111;
    page-break-inside: avoid;
  }

  .booking-confirmation__hero {
    gap: 2px;
    text-align: left;
  }

  .booking-confirmation__badge {
    margin: 0;
    padding: 0;
    background: transparent;
  }

  .booking-confirmation__hero h4 {
    font-size: 1.1rem;
  }

  .booking-confirmation__subtitle,
  .booking-confirmation__room-body p,
  .booking-confirmation__next-step-text,
  .booking-confirmation__direction-list p {
    line-height: 1.25;
  }

  .booking-confirmation__details {
    grid-template-columns: repeat(4, minmax(0, 1fr));
    gap: 6px;
  }

  .booking-confirmation__card,
  .booking-confirmation__room,
  .booking-confirmation__panel,
  .booking-confirmation__map,
  .booking-confirmation__next-step {
    padding: 7px;
    border-radius: 0;
    border: 1px solid #ddd;
    background: #fff;
    box-shadow: none;
  }

  .booking-confirmation__room {
    grid-template-columns: 44mm 1fr;
    gap: 8px;
  }

  .booking-confirmation__room-image {
    aspect-ratio: 4 / 3;
  }

  .booking-confirmation__info-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 6px;
  }

  .booking-confirmation__map-image {
    aspect-ratio: 2 / 1;
    max-height: 52mm;
  }

  .booking-confirmation__section-heading,
  .booking-confirmation__card-header,
  .booking-confirmation__next-step-label {
    font-size: 0.68rem;
    letter-spacing: 0.04em;
  }

  .booking-confirmation__card-value,
  .booking-confirmation__strong,
  .booking-confirmation__room-body h5 {
    font-size: 0.9rem;
  }

  .booking-confirmation__card-note,
  .booking-confirmation__line,
  .booking-confirmation__link-row,
  .booking-confirmation__room-body p,
  .booking-confirmation__direction-list p,
  .booking-confirmation__next-step-text,
  .booking-confirmation__extra {
    font-size: 0.78rem;
  }

  .booking-confirmation__extras {
    gap: 4px;
  }

  .booking-confirmation__extra {
    padding: 2px 5px;
    border-radius: 0;
  }
}

</style>
