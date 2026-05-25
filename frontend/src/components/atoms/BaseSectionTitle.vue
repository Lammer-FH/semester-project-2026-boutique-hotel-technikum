<script setup lang="ts">
import { computed } from "vue"
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

const alignClass = computed(() =>
  props.align === "center" ? "ion-text-center" : "ion-text-start"
)
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
  letter-spacing: var(--tracking-wide);
  font-size: var(--text-label-sm);
  color: var(--color-olive);
  margin-bottom: 6px;
}

.section-title__heading {
  font-size: var(--text-heading-lg);
}
</style>
