<script setup lang="ts">
import { IonButton, IonIcon } from "@ionic/vue"
import { calendarOutline } from "ionicons/icons"

const props = defineProps<{
  label: string
}>()

const emit = defineEmits<{
  (event: "open"): void
}>()

const handleOpen = () => {
  emit("open")
}
</script>

<template>
  <ion-button @click="handleOpen">
    <ion-icon slot="start" :icon="calendarOutline" />
    {{ props.label }}
  </ion-button>
</template>
