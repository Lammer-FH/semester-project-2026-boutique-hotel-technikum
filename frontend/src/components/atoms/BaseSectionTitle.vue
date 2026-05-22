<script setup lang="ts">
import { IonText } from "@ionic/vue"

const props = withDefaults(
  defineProps<{
    title: string
    subtitle?: string
    align?: "left" | "center"
  }>(),
  {
    align: "left",
  }
)

const alignClass = props.align === "center" ? "ion-text-center" : "ion-text-start"
</script>

<template>
  <div class="ion-margin-bottom" :class="alignClass">
    <ion-text v-if="subtitle" class="section-title__subtitle">
      {{ subtitle }}
    </ion-text>
    <h2 class="section-title__heading">{{ title }}</h2>
  </div>
</template>

<style scoped>
.section-title__subtitle {
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.75rem;
  color: var(--color-olive);
  margin-bottom: 6px;
}

.section-title__heading {
  font-size: 1.9rem;
}
</style>
