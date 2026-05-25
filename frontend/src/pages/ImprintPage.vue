<script setup lang="ts">
import {
  IonCard,
  IonCardContent,
} from "@ionic/vue"
import PageLayout from "@/components/layout/PageLayout.vue"
import SectionBlock from "@/components/molecules/SectionBlock.vue"
import { imprintDetails, imprintPageContent } from "@/data/content/imprintContent"
</script>

<template>
  <page-layout>
        <section-block
          :title="imprintPageContent.title"
          :subtitle="imprintPageContent.subtitle"
        >
          
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
        </section-block>
  </page-layout>
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