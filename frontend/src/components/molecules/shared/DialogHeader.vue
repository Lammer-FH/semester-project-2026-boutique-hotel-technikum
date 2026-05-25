<script setup lang="ts">
import { IonButton } from "@ionic/vue"

const props = defineProps<{
  eyebrow?: string
  title: string
  subtitle?: string
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
  <div class="dialog-header">
    <div>
      <p v-if="props.eyebrow" class="dialog-header__eyebrow">{{ props.eyebrow }}</p>
      <h3 class="dialog-header__title">{{ props.title }}</h3>
      <p v-if="props.subtitle" class="dialog-header__subtitle">{{ props.subtitle }}</p>
    </div>
    <ion-button fill="clear" @click="handleClose">{{ props.closeLabel }}</ion-button>
  </div>
</template>

<style scoped>
.dialog-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
}

.dialog-header__eyebrow {
  margin: 0 0 4px;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 0.7rem;
  color: var(--color-olive);
}

.dialog-header__title {
  margin: 0;
  font-size: 1.4rem;
}

.dialog-header__subtitle {
  margin: 6px 0 0;
  color: var(--color-olive);
  font-weight: 600;
}

@media (max-width: 640px) {
  .dialog-header {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
