<script setup lang="ts">
import {
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardSubtitle,
  IonCardTitle,
  IonChip,
  IonSkeletonText,
} from "@ionic/vue"
</script>

<template>
  <ion-card class="room-card">
    <div class="room-card__media">
      <ion-skeleton-text animated class="room-card__image" />
    </div>
    <ion-card-header>
      <div class="room-card__header">
        <ion-card-title>
          <ion-skeleton-text animated style="width: 60%" />
        </ion-card-title>
        <div class="room-card__price">
          <ion-skeleton-text animated style="width: 80px; height: 16px" />
          <ion-skeleton-text animated style="width: 60px; height: 10px" />
        </div>
      </div>
      <ion-card-subtitle class="room-card__subtitle">
        <ion-skeleton-text animated style="width: 120px; height: 12px" />
      </ion-card-subtitle>
    </ion-card-header>
    <ion-card-content>
      <ion-skeleton-text animated style="width: 100%; height: 12px" />
      <ion-skeleton-text animated style="width: 88%; height: 12px" />
      <ion-skeleton-text animated style="width: 74%; height: 12px" />
      <div class="room-card__extras">
        <ion-chip class="room-card__chip">
          <ion-skeleton-text animated style="width: 70px; height: 12px" />
        </ion-chip>
        <ion-chip class="room-card__chip">
          <ion-skeleton-text animated style="width: 60px; height: 12px" />
        </ion-chip>
        <ion-chip class="room-card__chip">
          <ion-skeleton-text animated style="width: 80px; height: 12px" />
        </ion-chip>
      </div>
    </ion-card-content>
  </ion-card>
</template>

<style scoped>
.room-card {
  --background: #fff;
  border-radius: 20px;
  background: linear-gradient(160deg, #fffaf3 0%, #f3e7d6 100%);
  border: 1px solid rgba(31, 27, 24, 0.08);
  box-shadow: 0 18px 30px rgba(31, 27, 24, 0.08);
  overflow: hidden;
}

.room-card__media {
  position: relative;
  overflow: hidden;
}

.room-card__image {
  width: 100%;
  height: 220px;
  border-radius: 0;
}

.room-card__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
}

.room-card__price {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 6px;
}

.room-card__subtitle {
  margin-top: 6px;
}

.room-card__extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 12px;
}

.room-card__chip {
  --background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.1);
}

@media (max-width: 640px) {
  .room-card__header {
    flex-direction: column;
    align-items: flex-start;
  }

  .room-card__price {
    align-items: flex-start;
  }
}
</style>
