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

<style scoped src="./room-card.shared.css"></style>

<style scoped>
.room-card__media {
  position: relative;
  overflow: hidden;
}

.room-card__image {
  width: 100%;
  height: 220px;
  border-radius: 0;
}

.room-card__price {
  gap: 6px;
}

.room-card__extras {
  margin-top: 12px;
}
</style>
