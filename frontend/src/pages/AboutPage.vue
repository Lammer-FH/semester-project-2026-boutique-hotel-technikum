<script setup lang="ts">
import { IonContent, IonPage } from "@ionic/vue"
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"
import StorySection from "@/components/organisms/StorySection.vue"
import FeatureGrid from "@/components/organisms/FeatureGrid.vue"
import ContactStrip from "@/components/organisms/ContactStrip.vue"
import TheHeader from "@/components/layout/TheHeader.vue"
import TheFooter from "@/components/layout/TheFooter.vue"
import {
  aboutContent,
  aboutPageContent,
  aboutValues,
  contactDetails,
  neighborhoodHighlights,
} from "@/data/hotelContent"
</script>

<template>
  <ion-page>
    <the-header />
    <ion-content class="page-shell">
      <div class="page-shell__inner">
        <div class="ion-margin-top ion-margin-bottom">
          <story-section
            :title="aboutContent.title"
            :paragraphs="aboutContent.paragraphs"
            :accent-title="aboutContent.accentTitle"
            :accent-text="aboutContent.accentText"
          />
        </div>

        <div class="ion-margin-top ion-margin-bottom">
          <base-section-title
            :title="aboutPageContent.valuesTitle"
            :subtitle="aboutPageContent.valuesSubtitle"
          />
          <feature-grid :items="aboutValues" />
        </div>

        <div class="ion-margin-top ion-margin-bottom">
          <base-section-title
            :title="aboutPageContent.neighborhoodTitle"
            :subtitle="aboutPageContent.neighborhoodSubtitle"
          />
          <feature-grid :items="neighborhoodHighlights" />
        </div>

        <contact-strip
          :phone="contactDetails.phone"
          :email="contactDetails.email"
          :address-line1="contactDetails.addressLine1"
          :address-line2="contactDetails.addressLine2"
        />
      </div>
      <the-footer />
    </ion-content>
  </ion-page>
</template>
