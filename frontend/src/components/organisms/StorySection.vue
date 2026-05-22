<script setup lang="ts">
import { IonCard, IonCardContent, IonCol, IonGrid, IonRow } from "@ionic/vue"

defineProps<{
  title: string
  paragraphs: string[]
  accentTitle?: string
  accentText?: string
}>()
</script>

<template>
  <ion-grid>
    <ion-row class="ion-align-items-start">
      <ion-col size="12" size-md="7">
        <h3 class="story__title">{{ title }}</h3>
        <p v-for="paragraph in paragraphs" :key="paragraph" class="story__paragraph">
          {{ paragraph }}
        </p>
      </ion-col>
      <ion-col v-if="accentTitle || accentText" size="12" size-md="5">
        <ion-card class="story__accent">
          <ion-card-content>
            <p class="story__accent-title">{{ accentTitle }}</p>
            <p class="story__accent-text">{{ accentText }}</p>
          </ion-card-content>
        </ion-card>
      </ion-col>
    </ion-row>
  </ion-grid>
</template>

<style scoped>
.story__title {
  font-size: 1.5rem;
}

.story__paragraph {
  font-size: 1.05rem;
  line-height: 1.85;
  color: #2b2b2b;
  margin-bottom: 14px;
}

.story__accent {
  border-radius: 18px;
  background: var(--color-midnight);
  color: #fff;
  box-shadow: 0 16px 30px rgba(15, 25, 32, 0.25);
  display: flex;
  flex-direction: column;
}

.story__accent > ion-card-content {
  flex: 1 1 auto;
}

.story__accent-title {
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  margin-bottom: 6px;
}

.story__accent-text {
  font-size: 1.05rem;
}

@media (min-width: 1024px) {
  .story__paragraph {
    font-size: 1.12rem;
  }
}
</style>
