<script setup lang="ts">
import { IonCard, IonCardContent, IonCardHeader, IonCardTitle, IonIcon } from "@ionic/vue"
import {
  bedOutline,
  globeOutline,
  leafOutline,
  locationOutline,
  mapOutline,
  personOutline,
  restaurantOutline,
  sparklesOutline,
} from "ionicons/icons"

const iconMap = {
  bed: bedOutline,
  globe: globeOutline,
  leaf: leafOutline,
  location: locationOutline,
  map: mapOutline,
  person: personOutline,
  restaurant: restaurantOutline,
  sparkles: sparklesOutline,
}

defineProps<{
  title: string
  description: string
  icon: keyof typeof iconMap
}>()
</script>

<template>
  <ion-card class="feature-card icon-box">
    <ion-card-header>
      <ion-icon :icon="iconMap[icon]" class="feature-card__icon" />
      <ion-card-title>{{ title }}</ion-card-title>
    </ion-card-header>
    <ion-card-content>
      {{ description }}
    </ion-card-content>
  </ion-card>
</template>

<style scoped>
.icon-box {
  --background: #fff;
  border-radius: 18px;
  background: linear-gradient(160deg, #fff9f0 0%, #f3e7d6 100%);
  border: 1px solid rgba(31, 27, 24, 0.08);
  box-shadow: 0 14px 28px rgba(31, 27, 24, 0.08);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  display: flex;
  flex-direction: column;
}

.icon-box:hover {
  transform: translateY(-3px);
  box-shadow: 0 18px 34px rgba(31, 27, 24, 0.12);
}

.icon-box ion-icon {
  color: var(--color-terracotta);
  font-size: 1.3rem;
}

.feature-card__icon {
  font-size: 1.5rem;
}

.feature-card > ion-card-content {
  flex: 1 1 auto;
}
</style>
