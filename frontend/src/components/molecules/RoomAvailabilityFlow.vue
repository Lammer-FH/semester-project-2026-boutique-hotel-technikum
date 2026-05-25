<script setup lang="ts">
import { ref } from "vue"
import { availabilityDialogContent } from "@/data/content/bookingContent"
import RoomAvailabilityTrigger from "@/components/molecules/availability/RoomAvailabilityTrigger.vue"
import RoomAvailabilityDialog from "@/components/molecules/availability/RoomAvailabilityDialog.vue"

const props = defineProps<{
  roomId: number
  roomTitle: string
  roomMaxGuests: number
}>()

const isOpen = ref(false)

const openDialog = () => {
  isOpen.value = true
}

const closeDialog = () => {
  isOpen.value = false
}
</script>

<template>
  <div class="availability-flow">
    <room-availability-trigger
      :label="availabilityDialogContent.triggerLabel"
      @open="openDialog"
    />
    <room-availability-dialog
      :is-open="isOpen"
      :room-id="props.roomId"
      :room-title="props.roomTitle"
      :room-max-guests="props.roomMaxGuests"
      @close="closeDialog"
    />
  </div>
</template>

<style scoped>
.availability-flow {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
</style>
