<script setup lang="ts">
import { computed } from "vue"
import {
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardSubtitle,
  IonCardTitle,
  IonChip,
  IonIcon,
  IonImg,
} from "@ionic/vue"
import {
  accessibilityOutline,
  bedOutline,
  briefcaseOutline,
  cafeOutline,
  homeOutline,
  leafOutline,
  peopleOutline,
  sparklesOutline,
  sunnyOutline,
  wifiOutline,
  wineOutline,
} from "ionicons/icons"
import type { Room } from "@/core/models/room"
import { formatPrice } from "@/core/formatters"
import RoomAvailabilityDialog from "@/components/molecules/RoomAvailabilityDialog.vue"

const props = defineProps<{
  room: Room
}>()

const iconMap: Record<string, string> = {
  accessible: accessibilityOutline,
  balcony: homeOutline,
  bed: bedOutline,
  breakfast: cafeOutline,
  minibar: wineOutline,
  spa: leafOutline,
  view: sunnyOutline,
  wifi: wifiOutline,
  workspace: briefcaseOutline,
}

const primaryImage = computed(() => {
  if (!props.room.images.length) {
    return {
      url: `/images/rooms/${props.room.id}.svg`,
      altText: `${props.room.title} room interior`,
    }
  }

  const sorted = [...props.room.images].sort((a, b) => a.sortOrder - b.sortOrder)
  return sorted[0]
})

const resolveExtraIcon = (iconName: string) => iconMap[iconName] ?? sparklesOutline
</script>

<template>
  <ion-card class="room-card">
    <ion-img class="room-card__image" :src="primaryImage.url" :alt="primaryImage.altText" />
    <ion-card-header>
      <div class="room-card__header">
        <ion-card-title>{{ room.title }}</ion-card-title>
        <div class="room-card__price">
          <span class="room-card__price-value">{{ formatPrice(room.basePricePerNight) }} EUR</span>
          <span class="room-card__price-note">per night</span>
        </div>
      </div>
      <ion-card-subtitle class="room-card__subtitle">
        <ion-icon :icon="peopleOutline" />
        Sleeps {{ room.maxGuests }}
      </ion-card-subtitle>
    </ion-card-header>
    <ion-card-content>
      <p class="room-card__description">{{ room.description }}</p>
      <div class="room-card__extras">
        <ion-chip
          v-for="extra in room.extras"
          :key="extra.id"
          class="room-card__chip"
        >
          <ion-icon :icon="resolveExtraIcon(extra.iconName)" />
          <span>{{ extra.title }}</span>
        </ion-chip>
      </div>
      <room-availability-dialog
        class="room-card__availability"
        :room-id="room.id"
        :room-title="room.title"
        :room-max-guests="room.maxGuests"
      />
    </ion-card-content>
  </ion-card>
</template>

<style scoped src="./room-card.shared.css"></style>

<style scoped>
.room-card {
  display: flex;
  flex-direction: column;
  align-self: start;
}

.room-card__image {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

.room-card__price {
  gap: 2px;
  font-weight: 600;
}

.room-card__price-value {
  color: var(--color-terracotta);
  font-size: 1.05rem;
}

.room-card__price-note {
  font-size: 0.75rem;
  color: var(--color-olive);
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.room-card__subtitle {
  display: flex;
  align-items: center;
  gap: 6px;
  font-weight: 600;
  color: var(--color-midnight);
}

.room-card__description {
  margin-bottom: 14px;
}

.room-card__availability {
  margin-top: 8px;
}

.room-card__chip {
  color: var(--color-ink);
  font-weight: 600;
}

.room-card__chip ion-icon {
  color: var(--color-terracotta);
}

</style>
