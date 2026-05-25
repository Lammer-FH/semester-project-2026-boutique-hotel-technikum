<script setup lang="ts">
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"

type Props = {
  title?: string
  subtitle?: string
  align?: "left" | "center"
  contentClass?: string
}

const props = withDefaults(defineProps<Props>(), {
  align: "left",
  contentClass: "ion-margin-top ion-margin-bottom",
})
</script>

<template>
  <section :class="props.contentClass">
    <base-section-title
      v-if="props.title"
      :title="props.title"
      :subtitle="props.subtitle"
      :align="props.align"
    />
    <slot />
  </section>
</template>
