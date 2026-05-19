<script setup lang="ts">
import { IonContent, IonModal } from "@ionic/vue"

const props = withDefaults(
  defineProps<{
    isOpen: boolean
    keepMounted?: boolean
    contentClass?: string
  }>(),
  {
    keepMounted: true,
    contentClass: "",
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
    @didDismiss="handleDismiss"
  >
    <ion-content :class="props.contentClass">
      <slot />
    </ion-content>
  </ion-modal>
</template>
