<script setup lang="ts">
import type { VNodeRef } from "vue"
import { computed, nextTick, onMounted, ref } from "vue"
import { IonButton } from "@ionic/vue"
import type { IonContent } from "@ionic/vue"
import type { ScrollDetail } from "@ionic/core/components"
import { storeToRefs } from "pinia"
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"
import ExtrasStrip from "@/components/organisms/ExtrasStrip.vue"
import RoomCard from "@/components/organisms/roomcard/RoomCard.vue"
import RoomCardSkeletonList from "@/components/organisms/roomcard/RoomCardSkeletonList.vue"
import PageLayout from "@/components/layout/PageLayout.vue"
import SectionBlock from "@/components/molecules/SectionBlock.vue"
import { useExtraStore } from "@/application/stores/extraStore"
import { useRoomStore } from "@/application/stores/roomStore"
import { extrasContent, roomsPageContent } from "@/data/content/roomsContent"
import { buildPaginationPages, getPaginationRange } from "@/core/pagination"
import { scrollIonContentToTop, updateIonContentRef } from "@/core/scroll"

const contentEl = ref<InstanceType<typeof IonContent> | null>(null)
const contentRef: VNodeRef = (refValue) => updateIonContentRef(contentEl, refValue)

const PAGINATION_SHOW_THRESHOLD = 240
const scrolledDown = ref(false)

const roomStore = useRoomStore()
const {
  rooms,
  isLoading,
  currentPage,
  pageSize,
  hasRooms,
  totalPages,
  totalRooms,
  isReady,
} = storeToRefs(roomStore)

const extraStore = useExtraStore()
const {
  extras,
  isLoading: extrasLoading,
} = storeToRefs(extraStore)

const pages = computed(() => buildPaginationPages(totalPages.value))
const visibleRange = computed(() =>
  getPaginationRange({
    currentPage: currentPage.value,
    pageSize: pageSize.value,
    totalItems: totalRooms.value,
    currentCount: rooms.value.length,
  })
)

const roomsMetaLabel = computed(() =>
  roomsPageContent.roomsMeta(visibleRange.value.start, visibleRange.value.end, totalRooms.value)
)

const setPage = (page: number) => {
  roomStore.setPage(page)
  nextTick(() => scrollIonContentToTop(contentEl.value))
}

const prefetchPage = (page: number) => {
  void roomStore.prefetchRooms(page, pageSize.value)
}

const retryFetch = () => {
  void roomStore.fetchRooms({
    page: currentPage.value,
    size: pageSize.value,
    force: true,
  })
}

const onContentScroll = (detail: ScrollDetail) => {
  if (!scrolledDown.value && detail.scrollTop > PAGINATION_SHOW_THRESHOLD) {
    scrolledDown.value = true
  }
}

onMounted(() => {
  roomStore.fetchRooms()
  extraStore.getExtras()
})
</script>

<template>
  <page-layout
    :content-ref="contentRef"
    content-class="page-shell"
    layout-class="rooms-page__layout"
    inner-class="page-shell__inner rooms-page"
    footer-class="rooms-page__footer"
    @scroll="onContentScroll"
  >
          <section-block>
            <div class="rooms-page__intro" v-once>
              <base-section-title
                :title="roomsPageContent.title"
                :subtitle="roomsPageContent.subtitle"
              />
              <p class="rooms-page__lead">
                {{ roomsPageContent.lead }}
              </p>
            </div>
          </section-block>

          <extras-strip
            class="rooms-page__extras"
            :title="extrasContent.title"
            :subtitle="extrasContent.subtitle"
            :empty-message="extrasContent.emptyMessage"
            :extras="extras"
            :is-loading="extrasLoading"
          />

          <div class="rooms-page__meta" v-if="hasRooms && isReady">
            <span>
              {{ roomsMetaLabel }}
            </span>
          </div>

          <div class="rooms-page__list" v-if="hasRooms || isLoading">
            <room-card-skeleton-list v-if="isLoading" :count="pageSize" />
            <room-card v-else v-for="room in rooms" :key="room.id" :room="room" v-memo="[room]" />
          </div>

          <div class="rooms-page__empty" v-if="isReady && !hasRooms">
            <h3 class="rooms-page__empty-title">{{ roomsPageContent.emptyTitle }}</h3>
            <p class="rooms-page__empty-body">{{ roomsPageContent.emptyBody }}</p>
            <ion-button size="small" @click="retryFetch">
              {{ roomsPageContent.emptyAction }}
            </ion-button>
          </div>

          <div
            class="rooms-page__pagination"
            :class="{ 'rooms-page__pagination--visible': scrolledDown }"
            :aria-hidden="!scrolledDown"
            :inert="!scrolledDown"
            v-if="totalPages > 1 && isReady"
          >
            <div class="rooms-page__pagination-label">{{ roomsPageContent.paginationLabel }}</div>
            <div
              class="rooms-page__pagination-buttons"
              role="group"
              :aria-label="roomsPageContent.paginationAriaLabel"
            >
              <ion-button
                v-for="page in pages"
                :key="page"
                size="small"
                :fill="page === currentPage ? 'solid' : 'outline'"
                :aria-current="page === currentPage ? 'page' : undefined"
                :disabled="page === currentPage"
                @mouseenter="prefetchPage(page)"
                @focus="prefetchPage(page)"
                @click="setPage(page)"
              >
                {{ page }}
              </ion-button>
            </div>
          </div>

  </page-layout>
</template>

<style scoped>
.rooms-page {
  display: flex;
  flex: 1;
  flex-direction: column;
  min-height: 100%;
  padding-bottom: 32px;
}


.rooms-page__layout {
  display: flex;
  flex: 1;
  flex-direction: column;
  min-height: 100%;
}

.rooms-page__intro {
  margin-bottom: 24px;
}

.rooms-page__extras {
  margin-bottom: 20px;
}

.rooms-page__lead {
  max-width: 620px;
  font-size: 1.02rem;
}

.rooms-page__meta {
  font-weight: 600;
  margin-bottom: 16px;
  color: var(--color-midnight);
}

.rooms-page__list {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
  margin-bottom: 28px;
}

.rooms-page__pagination {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 8px 16px 12px;
  position: sticky;
  bottom: 0;
  z-index: 10;
  background: rgba(255, 249, 240, 0.55);
  border-radius: var(--radius-md) var(--radius-md) 0 0;
  margin: 0 auto;
  width: fit-content;
  max-width: 100%;
  backdrop-filter: blur(4px);
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.25s ease;
}

.rooms-page__pagination--visible {
  opacity: 1;
  pointer-events: auto;
}

.rooms-page__pagination-label {
  text-transform: uppercase;
  letter-spacing: var(--tracking-medium);
  font-size: var(--text-label-sm);
  color: var(--color-midnight);
  font-weight: 700;
}

.rooms-page__pagination-buttons {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  justify-content: center;
}

.rooms-page__empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 12px;
  padding: 32px 16px 40px;
  background: var(--color-cream);
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-soft);
}

.rooms-page__empty-title {
  margin: 0;
  font-size: 1.3rem;
}

.rooms-page__empty-body {
  margin: 0;
  max-width: 520px;
  color: var(--color-midnight);
}

@media (min-width: 768px) {
  .rooms-page__list {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (min-width: 1100px) {
  .rooms-page__list {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}
</style>
