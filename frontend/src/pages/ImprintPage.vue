<script setup lang="ts">
import {
  IonCard,
  IonCardContent,
  IonContent,
  IonPage,
} from "@ionic/vue"
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"
import TheHeader from "@/components/layout/TheHeader.vue"
import TheFooter from "@/components/layout/TheFooter.vue"
import { imprintDetails } from "@/data/hotelContent"
</script>

<template>
  <ion-page>
    <the-header />
    <ion-content class="page-shell">
      <div class="page-shell__inner">
        <div class="ion-margin-top ion-margin-bottom">
          <base-section-title title="Imprint" subtitle="Legal information" />
          
          <ion-card class="imprint-card">
            <ion-card-content>
              <div class="imprint-container">
                <section v-for="section in imprintDetails.sections" :key="section.title" class="imprint-section">
                  <h3 class="imprint-section-title">{{ section.title }}</h3>
                  <p v-for="(line, index) in section.lines" :key="index" class="imprint-text">
                    {{ line }}
                  </p>
                </section>
              </div>
            </ion-card-content>
          </ion-card>
        </div>
      </div>
      <the-footer />
    </ion-content>
  </ion-page>
</template>

<style scoped>
.imprint-card {
  border-radius: 20px;
  background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.08);
  box-shadow: 0 16px 30px rgba(31, 27, 24, 0.08);
  display: flex;
  flex-direction: column;
}

.imprint-card > ion-card-content {
  flex: 1 1 auto;
}

.imprint-section {
  margin-bottom: 24px;
}

.imprint-section:last-child {
  margin-bottom: 0;
}

.imprint-section-title {
  font-weight: bold;
  font-size: 1.1rem;
  margin-bottom: 8px;
  color: var(--ion-color-dark);
  border-bottom: 1px solid var(--ion-color-light);
  padding-bottom: 4px;
}

.imprint-text {
  margin: 4px 0;
  line-height: 1.5;
  color: var(--ion-color-medium-shade);
}
</style>