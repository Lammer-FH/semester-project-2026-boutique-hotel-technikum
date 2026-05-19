<script setup lang="ts">
import { IonButton } from "@ionic/vue"

const props = defineProps<{
  eyebrow: string
  title: string
  dateRangeLabel: string
  closeLabel: string
}>()

const emit = defineEmits<{
  (event: "close"): void
}>()

const handleClose = () => {
  emit("close")
}
</script>

<template>
  <div class="booking-dialog__header">
    <div>
      <p class="booking-dialog__eyebrow">{{ props.eyebrow }}</p>
      <h3 class="booking-dialog__title">{{ props.title }}</h3>
      <p v-if="props.dateRangeLabel" class="booking-dialog__dates">{{ props.dateRangeLabel }}</p>
    </div>
    <ion-button fill="clear" @click="handleClose">{{ props.closeLabel }}</ion-button>
  </div>
</template>

<style scoped>
.booking-dialog__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
}

.booking-dialog__eyebrow {
  margin: 0 0 4px;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
}

.booking-dialog__title {
  margin: 0;
  font-size: 1.4rem;
}

.booking-dialog__dates {
  margin: 6px 0 0;
  color: var(--color-olive);
  font-weight: 600;
}

@media (max-width: 640px) {
  .booking-dialog__header {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
