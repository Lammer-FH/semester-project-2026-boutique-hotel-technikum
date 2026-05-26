<script setup lang="ts">
import type { VNodeRef } from "vue"
import { IonContent, IonPage } from "@ionic/vue"
import TheHeader from "@/components/layout/TheHeader.vue"
import TheFooter from "@/components/layout/TheFooter.vue"

type ContentRef = VNodeRef

const props = withDefaults(
  defineProps<{
    contentClass?: string
    layoutClass?: string
    innerClass?: string
    footerClass?: string
    contentRef?: ContentRef
  }>(),
  {
    contentClass: "page-shell",
    layoutClass: "",
    innerClass: "page-shell__inner",
    footerClass: "",
  }
)
</script>

<template>
  <ion-page>
    <the-header />
    <ion-content :ref="props.contentRef" :class="props.contentClass">
      <div class="page-shell__layout" :class="props.layoutClass">
        <div :class="props.innerClass">
          <slot />
        </div>
      </div>
    </ion-content>
    <slot name="footer">
      <the-footer :class="props.footerClass" />
    </slot>
  </ion-page>
</template>
