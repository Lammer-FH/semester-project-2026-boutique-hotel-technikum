<script setup lang="ts">
import { computed } from "vue"
import { IonChip, IonIcon, IonSkeletonText } from "@ionic/vue"
import type { Extra } from "@/core/models/room"
import { resolveExtraIcon } from "@/core/extraIcons"
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"

const props = defineProps<{
  title: string
  subtitle?: string
  emptyMessage?: string
  extras: Extra[]
  isLoading: boolean
}>()

const emptyStateMessage = computed(() => props.emptyMessage ?? "No extras available right now.")
</script>

<template>
  <section class="extras-strip">
    <base-section-title :title="props.title" :subtitle="props.subtitle" />

    <p v-if="!props.isLoading && !props.extras.length" class="extras-strip__empty">
      {{ emptyStateMessage }}
    </p>

    <div class="extras-strip__list">
      <template v-if="props.isLoading">
        <ion-skeleton-text
          v-for="index in 6"
          :key="`extra-skeleton-${index}`"
          animated
          class="extras-strip__skeleton"
        />
      </template>
      <ion-chip v-else v-for="extra in props.extras" :key="extra.id" class="extras-strip__chip">
        <ion-icon :icon="resolveExtraIcon(extra.iconName)" />
        <span>{{ extra.title }}</span>
      </ion-chip>
    </div>
  </section>
</template>

<style scoped>
.extras-strip {
  display: grid;
  gap: 12px;
}

.extras-strip__list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.extras-strip__chip {
  --background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.1);
  color: var(--color-ink);
  font-weight: 600;
}

.extras-strip__chip ion-icon {
  color: var(--color-terracotta);
}

.extras-strip__skeleton {
  width: 120px;
  height: 28px;
  border-radius: 999px;
}

.extras-strip__empty {
  margin: 0;
  color: var(--color-olive);
  font-weight: 600;
}
</style>
