<script setup lang="ts">
import { IonContent, IonModal } from "@ionic/vue"

// Wraps IonModal with common defaults for consistent dialog content behavior.
const props = withDefaults(
  defineProps<{
    isOpen: boolean
    keepMounted?: boolean
    contentClass?: string
    modalClass?: string
  }>(),
  {
    keepMounted: true,
    contentClass: "",
    modalClass: "",
  }
)

const emit = defineEmits<{
  (event: "close"): void
}>()

const handleDismiss = () => {
  emit("close")
}
</script>

<template>
  <ion-modal
    :is-open="props.isOpen"
    :keep-contents-mounted="props.keepMounted"
    :css-class="props.modalClass"
    @didDismiss="handleDismiss"
  >
    <ion-content :class="props.contentClass">
      <slot />
    </ion-content>
  </ion-modal>
</template>
