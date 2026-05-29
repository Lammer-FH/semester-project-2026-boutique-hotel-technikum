<script setup lang="ts">
const props = defineProps<{
  message: string
  detail?: string
}>()
</script>

<template>
  <div class="error-banner" role="status">
    <p class="error-banner__message">{{ props.message }}</p>
    <p v-if="props.detail" class="error-banner__detail">{{ props.detail }}</p>
  </div>
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

.error-banner__message,
.error-banner__detail {
  margin: 0;
}

.error-banner__detail {
  margin-top: 4px;
  color: rgba(79, 46, 30, 0.9);
  font-weight: 500;
  font-size: 0.92rem;
}
</style>
