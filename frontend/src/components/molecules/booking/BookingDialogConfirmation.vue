<script setup lang="ts">
import { IonButton } from "@ionic/vue"

const props = defineProps<{
  title: string
  bookingIdText: string
  dateRangeLabel: string
  doneLabel: string
}>()

const emit = defineEmits<{
  (event: "done"): void
}>()

const handleDone = () => {
  emit("done")
}
</script>

<template>
  <div class="booking-dialog__section">
    <div class="booking-confirmation">
      <h4>{{ props.title }}</h4>
      <p v-if="props.bookingIdText">{{ props.bookingIdText }}</p>
      <p v-if="props.dateRangeLabel">{{ props.dateRangeLabel }}</p>
    </div>
    <div class="booking-dialog__actions">
      <ion-button @click="handleDone">{{ props.doneLabel }}</ion-button>
    </div>
  </div>
</template>

<style scoped>
.booking-dialog__section {
  display: grid;
  gap: 16px;
}

.booking-confirmation {
  text-align: center;
  padding: 20px 14px;
  border-radius: 16px;
  background: #fffaf3;
  border: 1px solid rgba(31, 27, 24, 0.08);
  display: grid;
  gap: 8px;
}

.booking-dialog__actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

@media (max-width: 640px) {
  .booking-dialog__actions {
    flex-direction: column;
    align-items: stretch;
  }

  .booking-dialog__actions ion-button {
    width: 100%;
  }
}
</style>
