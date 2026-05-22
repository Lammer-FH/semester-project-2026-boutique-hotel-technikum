<script setup lang="ts">
import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCol,
  IonGrid,
  IonIcon,
  IonRow,
  IonText,
} from "@ionic/vue"
import { callOutline, mailOutline, locationOutline } from "ionicons/icons"

defineProps<{
  phone: string
  email: string
  addressLine1: string
  addressLine2: string
}>()
</script>

<template>
  <ion-card class="contact-card ion-margin-top">
    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-align-items-center">
          <ion-col size="12" size-md="7">
            <ion-text class="contact-strip__title">Ready for your stay?</ion-text>
            <p class="contact-strip__address">
              <ion-icon :icon="locationOutline" />
              {{ addressLine1 }} · {{ addressLine2 }}
            </p>
          </ion-col>
          <ion-col size="12" size-md="5">
            <div class="ion-margin-top contact-strip__actions">
              <ion-button :href="`mailto:${email}`" fill="outline">
                <ion-icon slot="start" :icon="mailOutline" />
                Send us an email
              </ion-button>
              <ion-button class="contact-strip__phone" :href="`tel:${phone}`">
                <ion-icon slot="start" :icon="callOutline" />
                Call us
              </ion-button>
            </div>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<style scoped>
.contact-card {
  border-radius: 20px;
  background: linear-gradient(160deg, #1f2f3a 0%, #162229 100%);
  color: #fff;
  box-shadow: 0 18px 36px rgba(15, 25, 32, 0.28);
  display: flex;
  flex-direction: column;
}

.contact-card > ion-card-content {
  flex: 1 1 auto;
}

.contact-strip__title {
  color: #fff;
  display: block;
  margin-bottom: 10px;
}

.contact-strip__address {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.95rem;
}

.contact-strip__actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  align-items: center;
}
</style>
