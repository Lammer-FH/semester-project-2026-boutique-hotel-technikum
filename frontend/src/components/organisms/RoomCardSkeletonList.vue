<script setup lang="ts">
import RoomCardSkeleton from "@/components/organisms/RoomCardSkeleton.vue"

const props = defineProps<{
  count: number
}>()
</script>

<template>
  <room-card-skeleton v-for="index in props.count" :key="`skeleton-${index}`" />
</template>
