<script setup lang="ts">
import { IonButton, IonIcon } from "@ionic/vue"
import { closeOutline } from "ionicons/icons"

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
    <div class="dialog-header__left">
      <p v-if="props.eyebrow" class="dialog-header__eyebrow">{{ props.eyebrow }}</p>
      <div class="dialog-header__title-row">
        <h3 class="dialog-header__title">{{ props.title }}</h3>
      </div>
      <p v-if="props.subtitle" class="dialog-header__subtitle">{{ props.subtitle }}</p>
    </div>

    <ion-button
      fill="clear"
      class="dialog-header__close"
      :aria-label="props.closeLabel"
      :title="props.closeLabel"
      @click="handleClose"
    >
      <template v-slot:icon-only>
<ion-icon :icon="closeOutline"  />
</template>
    </ion-button>
  </div>
</template>

<style scoped>

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}

.dialog-header__left {
  display: flex;
  flex-direction: column;
}

.dialog-header__left {
  flex: 1 1 auto;
  min-width: 0;
}

.dialog-header__title-row {
  display: flex;
  align-items: center;
  gap: 8px;
}

.dialog-header__title {
  margin: 0;
  font-size: 1.4rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dialog-header__close {
  --padding-start: 0;
  --padding-end: 0;
  height: 36px;
  width: 36px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

@media (max-width: 640px) {
  .dialog-header {
    flex-direction: row;
    align-items: center;
  }
}
</style>
