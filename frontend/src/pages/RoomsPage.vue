<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { IonButton, IonContent, IonPage } from "@ionic/vue"
import { storeToRefs } from "pinia"
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"
import RoomCard from "@/components/organisms/RoomCard.vue"
import RoomCardSkeleton from "@/components/organisms/RoomCardSkeleton.vue"
import TheHeader from "@/components/layout/TheHeader.vue"
import TheFooter from "@/components/layout/TheFooter.vue"
import { useRoomStore } from "@/application/stores/roomStore"

const pageSize = 5
const currentPage = ref(1)

const roomStore = useRoomStore()
const { rooms, isLoading, error: errorMessage, pagination } = storeToRefs(roomStore)

const totalPages = computed(() => pagination.value?.totalPages ?? 1)
const pages = computed(() => Array.from({ length: totalPages.value }, (_, index) => index + 1))
const totalRooms = computed(() => pagination.value?.total ?? rooms.value.length)

const startNumber = computed(() => {
  if (!rooms.value.length) {
    return 0
  }
  return (currentPage.value - 1) * pageSize + 1
})

const endNumber = computed(() => {
  if (!rooms.value.length) {
    return 0
  }
  return Math.min(currentPage.value * pageSize, totalRooms.value)
})

const setPage = (page: number) => {
  currentPage.value = page
}

watch(currentPage, (page) => roomStore.getRooms(page, pageSize), { immediate: true })
</script>

<template>
  <ion-page>
    <the-header />
    <ion-content class="page-shell">
      <div class="rooms-page__layout">
        <div class="page-shell__inner rooms-page">
          <div class="rooms-page__intro">
            <base-section-title
              title="Rooms"
              subtitle="Choose a space that fits your rhythm in Vienna"
            />
            <p class="rooms-page__lead">
              Explore our boutique rooms and suites, each curated with local design details, restorative
              textures, and thoughtful extras.
            </p>
          </div>

          <div class="rooms-page__meta" v-if="rooms.length && !isLoading">
            <span>
              Showing {{ startNumber }}-{{ endNumber }} of {{ totalRooms }} rooms
            </span>
          </div>

          <div class="rooms-page__error" v-if="!isLoading && errorMessage">
            {{ errorMessage }}
          </div>

          <div class="rooms-page__list">
            <room-card-skeleton v-if="isLoading" v-for="index in pageSize" :key="`skeleton-${index}`" />
            <room-card v-else v-for="room in rooms" :key="room.id" :room="room" />
          </div>

          <div class="rooms-page__pagination" v-if="totalPages > 1 && !isLoading">
            <div class="rooms-page__pagination-label">Page</div>
            <div class="rooms-page__pagination-buttons" role="group" aria-label="Room pages">
              <ion-button
                v-for="page in pages"
                :key="page"
                size="small"
                :fill="page === currentPage ? 'solid' : 'outline'"
                :aria-current="page === currentPage ? 'page' : undefined"
                :disabled="page === currentPage"
                @click="setPage(page)"
              >
                {{ page }}
              </ion-button>
            </div>
          </div>
        </div>
        <the-footer class="rooms-page__footer" />
      </div>
    </ion-content>
  </ion-page>
</template>

<style scoped>
.rooms-page {
  padding-top: 24px;
  padding-bottom: 32px;
}


.rooms-page__layout {
  min-height: 100%;
  display: flex;
  flex-direction: column;
}

.rooms-page__footer {
  margin-top: auto;
}

.rooms-page__intro {
  margin-bottom: 24px;
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

.rooms-page__error {
  margin-bottom: 16px;
  padding: 12px 16px;
  border-radius: 14px;
  background: #fff4f0;
  border: 1px solid rgba(161, 79, 54, 0.2);
  color: var(--color-terracotta);
  font-weight: 600;
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
  padding-bottom: 20px;
}

.rooms-page__pagination-label {
  text-transform: uppercase;
  letter-spacing: 0.1em;
  font-size: 0.75rem;
  color: var(--color-olive);
  font-weight: 600;
}

.rooms-page__pagination-buttons {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  justify-content: center;
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
