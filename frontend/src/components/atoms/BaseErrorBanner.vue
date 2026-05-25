<script setup lang="ts">
const props = defineProps<{
  message: string
}>()
</script>

<template>
  <p class="error-banner" role="status">
    {{ props.message }}
  </p>
</template>

<style scoped>
.error-banner {
  margin: 0;
  padding: 12px 16px;
  border-radius: 14px;
  background: #fff4f0;
  border: 1px solid rgba(161, 79, 54, 0.2);
  color: var(--color-terracotta);
  font-weight: 600;
}
</style>
