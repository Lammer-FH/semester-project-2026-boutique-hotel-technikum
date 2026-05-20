<script setup lang="ts">
import { IonButton } from "@ionic/vue"

const props = defineProps<{
  title: string
  roomTitle: string
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
  <div class="availability-dialog__header">
    <div>
      <p class="availability-dialog__eyebrow">{{ props.title }}</p>
      <h3 class="availability-dialog__title">{{ props.roomTitle }}</h3>
    </div>
    <ion-button fill="clear" @click="handleClose">{{ props.closeLabel }}</ion-button>
  </div>
</template>

<style scoped>
.availability-dialog__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
}

.availability-dialog__eyebrow {
  margin: 0 0 4px;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
}

.availability-dialog__title {
  margin: 0;
  font-size: 1.4rem;
}

@media (max-width: 640px) {
  .availability-dialog__header {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
