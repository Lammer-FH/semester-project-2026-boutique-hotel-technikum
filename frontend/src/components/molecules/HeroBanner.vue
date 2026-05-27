<script setup lang="ts">
import { } from "vue"
import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCol,
  IonGrid,
  IonIcon,
  IonRow,
  IonText,
} from "@ionic/vue"
import { bedOutline, cafeOutline, leafOutline, calendarOutline, informationCircleOutline } from "ionicons/icons"
import type { RouteLocationRaw } from "vue-router"
import { heroBannerContent } from "@/data/content/landingContent"

const props = defineProps<{
  title: string
  subtitle: string
  primaryLabel: string
  primaryRoute: RouteLocationRaw
  secondaryLabel: string
  secondaryRoute: RouteLocationRaw
  backgroundImage?: string
  heroClass?: string
}>()


const heroPanelIcons = {
  bed: bedOutline,
  cafe: cafeOutline,
  leaf: leafOutline,
} as const
</script>

<template>
  <ion-card :class="['hero-card', props.heroClass]">
    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-align-items-center">
          <ion-col size="12" size-md="9">
            <ion-text class="hero__eyebrow">{{ heroBannerContent.eyebrow }}</ion-text>
            <h1 class="hero__title">{{ title }}</h1>
            <p class="hero__subtitle">{{ subtitle }}</p>
            <div class="hero-actions ion-margin-top ion-margin-bottom">
              <ion-button :router-link="primaryRoute">
                <ion-icon slot="start" :icon="calendarOutline" />
                {{ primaryLabel }}
              </ion-button>
              <ion-button :router-link="secondaryRoute" fill="outline">
                <ion-icon slot="start" :icon="informationCircleOutline" />
                {{ secondaryLabel }}
              </ion-button>
            </div>
          </ion-col>
          <ion-col size="12" size-md="3">
            <ion-card class="hero-panel">
              <ion-card-content>
                <ion-text class="hero__panel-title">{{ heroBannerContent.panelTitle }}</ion-text>
                <ul class="hero-panel-list" role="list">
                  <li
                    v-for="item in heroBannerContent.panelItems"
                    :key="item.label"
                    class="hero-panel-item"
                  >
                    <ion-icon :icon="heroPanelIcons[item.icon]" />
                    {{ item.label }}
                  </li>
                </ul>
              </ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<style scoped>
.hero-card {
  border-radius: var(--radius-lg);
  background: var(--color-cream);
  box-shadow: var(--shadow-soft);
  display: flex;
  flex-direction: column;
  min-height: 60vh;
  background-position: center;
  background-size: cover;
}

.hero-card > ion-card-content {
  flex: 1 1 auto;
}

.hero__eyebrow {
  text-transform: uppercase;
  letter-spacing: var(--tracking-wider);
  font-size: var(--text-label-sm);
  color: #fff;
  font-weight: 600;
  text-shadow: 0 3px 8px rgba(0,0,0,0.6);
  margin-bottom: 10px;
}

.hero-card ion-button:not([fill="outline"]) {
  --background: var(--color-terracotta);
  --color: #fff;
  border: none;
  box-shadow: 0 6px 18px rgba(0,0,0,0.28);
}

.hero-card ion-button[fill="outline"] {
  --border-color: rgba(255,255,255,0.95);
  --border-style: solid;
  --color: #fff;
  --background: rgba(255,255,255,0.06);
}

.hero-card ion-button:hover {
  opacity: 0.95;
}

.hero__title {
  font-size: var(--text-heading-xl);
  color: inherit;
  font-weight: 700;
  text-shadow: 0 6px 18px rgba(0,0,0,0.6);
}

.hero__subtitle {
  font-size: 1.05rem;
  max-width: 820px;
  color: inherit;
  text-shadow: 0 4px 12px rgba(0,0,0,0.55);
}

.hero--landing {
  background: linear-gradient(135deg, rgba(0,0,0,0.45) 0%, rgba(0,0,0,0.25) 100%), url('/images/landingpage.jpg') center/cover no-repeat;
  color: #fff;
}

.hero-panel {
  background: rgba(255,255,255,0.92);
  border-radius: var(--radius-sm);
  border: 1px solid rgba(0,0,0,0.06);
  display: flex;
  flex-direction: column;
}

.hero-panel > ion-card-content {
  flex: 1 1 auto;
}

.hero-panel-list {
  list-style: none;
  margin: 0;
  padding: 0;
  display: grid;
  gap: 10px;
}

.hero-panel-item {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 0.95rem;
  color: var(--color-ink);
}

.hero-panel-item ion-icon {
  color: var(--color-terracotta);
  font-size: 1.05rem;
}

.hero-actions {
  display: flex;
  gap: 1rem;
  align-items: center;
  flex-wrap: wrap;
}

.hero-actions ion-icon {
  font-size: 1.05rem;
}

.hero__panel-title {
  font-family: var(--app-font-display);
  font-size: 1.05rem;
  display: block;
  margin-bottom: 18px;
}

@media (min-width: 768px) {
  .hero__title {
    font-size: 3rem;
  }
}
</style>
