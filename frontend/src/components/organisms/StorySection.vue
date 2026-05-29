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
        <p
          v-for="(paragraph, index) in paragraphs"
          :key="`${index}-${paragraph.slice(0, 20)}`"
          class="story__paragraph"
        >
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
  font-size: var(--text-heading-md);
}

.story__paragraph {
  font-size: 1.05rem;
  line-height: 1.85;
  color: #2b2b2b;
  margin-bottom: 14px;
}

.story__accent {
  border-radius: var(--radius-md);
  background: var(--color-midnight);
  color: #fff;
  box-shadow: var(--shadow-accent);
  display: flex;
  flex-direction: column;
}

.story__accent > ion-card-content {
  flex: 1 1 auto;
}

.story__accent-title {
  text-transform: uppercase;
  letter-spacing: var(--tracking-wide);
  font-size: var(--text-label-xs);
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
