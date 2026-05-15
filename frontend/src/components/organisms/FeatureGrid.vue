<script setup lang="ts">
import { IonCol, IonGrid, IonRow } from "@ionic/vue"
import IconBoxCard from "@/components/molecules/IconBoxCard.vue"

type FeatureItem = {
  key: string
  title: string
  description: string
  icon: "bed" | "globe" | "leaf" | "location" | "map" | "person" | "restaurant" | "sparkles"
}

defineProps<{
  items: FeatureItem[]
}>()
</script>

<template>
  <ion-grid class="feature-grid">
    <ion-row>
      <ion-col
        v-for="item in items"
        :key="item.key"
        size="12"
        size-md="4"
      >
        <icon-box-card
          :title="item.title"
          :description="item.description"
          :icon="item.icon"
        />
      </ion-col>
    </ion-row>
  </ion-grid>
</template>

<style scoped>
.feature-grid ion-row {
  align-items: stretch;
}

.feature-grid ion-col {
  display: flex;
}

.feature-grid :deep(.icon-box),
.feature-grid :deep(.feature-card) {
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;
}

.feature-grid :deep(.icon-box ion-card-content),
.feature-grid :deep(.feature-card ion-card-content) {
  flex: 1 1 auto;
}
</style>
