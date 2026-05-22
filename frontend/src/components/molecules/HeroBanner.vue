<script setup lang="ts">
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
import { bedOutline, cafeOutline, leafOutline } from "ionicons/icons"
import type { RouteLocationRaw } from "vue-router"

defineProps<{
  title: string
  subtitle: string
  primaryLabel: string
  primaryRoute: RouteLocationRaw
  secondaryLabel: string
  secondaryRoute: RouteLocationRaw
}>()
</script>

<template>
  <ion-card class="hero-card">
    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-align-items-center">
          <ion-col size="12" size-md="8">
            <ion-text class="hero__eyebrow">Boutique stay in Vienna</ion-text>
            <h1 class="hero__title">{{ title }}</h1>
            <p class="hero__subtitle">{{ subtitle }}</p>
            <div class="ion-margin-top ion-margin-bottom">
              <ion-button :router-link="primaryRoute">{{ primaryLabel }}</ion-button>
              <ion-button :router-link="secondaryRoute" fill="outline">{{ secondaryLabel }}</ion-button>
            </div>
          </ion-col>
          <ion-col size="12" size-md="4">
            <ion-card class="hero-panel">
              <ion-card-content>
                <ion-text class="hero__panel-title">At a glance</ion-text>
                <ul class="hero-panel-list" role="list">
                  <li class="hero-panel-item">
                    <ion-icon :icon="bedOutline" />
                    28 rooms with custom details
                  </li>
                  <li class="hero-panel-item">
                    <ion-icon :icon="cafeOutline" />
                    Late breakfast until 11:30
                  </li>
                  <li class="hero-panel-item">
                    <ion-icon :icon="leafOutline" />
                    Quiet courtyard lounge
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
  border-radius: 24px;
  background: linear-gradient(135deg, #f8efe0 0%, #e9dcc8 100%);
  box-shadow: 0 16px 30px rgba(31, 27, 24, 0.08);
  display: flex;
  flex-direction: column;
}

.hero-card > ion-card-content {
  flex: 1 1 auto;
}

.hero__eyebrow {
  text-transform: uppercase;
  letter-spacing: 0.14em;
  font-size: 0.75rem;
  color: var(--color-terracotta);
  margin-bottom: 10px;
}

.hero__title {
  font-size: 2.4rem;
}

.hero__subtitle {
  font-size: 1rem;
  max-width: 520px;
}

.hero-panel {
  background: var(--color-cream);
  border-radius: 16px;
  border: 1px solid var(--color-mist);
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
