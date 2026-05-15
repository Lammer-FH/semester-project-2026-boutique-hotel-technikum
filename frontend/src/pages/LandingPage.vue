<script setup lang="ts">
import { IonContent, IonPage } from "@ionic/vue"
import BaseSectionTitle from "@/components/atoms/BaseSectionTitle.vue"
import HeroBanner from "@/components/molecules/HeroBanner.vue"
import FeatureGrid from "@/components/organisms/FeatureGrid.vue"
import StorySection from "@/components/organisms/StorySection.vue"
import ContactStrip from "@/components/organisms/ContactStrip.vue"
import TheHeader from "@/components/layout/TheHeader.vue"
import TheFooter from "@/components/layout/TheFooter.vue"
import {
  contactDetails,
  experienceHighlights,
  heroContent,
  hotelHighlights,
  hotelStory,
} from "@/data/hotelContent"
</script>

<template>
  <ion-page>
    <the-header />
    <ion-content class="page-shell">
      <div class="page-shell__inner">
        <hero-banner
          :title="heroContent.title"
          :subtitle="heroContent.subtitle"
          :primary-label="heroContent.primaryLabel"
          :primary-route="heroContent.primaryRoute"
          :secondary-label="heroContent.secondaryLabel"
          :secondary-route="heroContent.secondaryRoute"
        />

        <div class="ion-margin-top ion-margin-bottom">
          <base-section-title
            title="Why guests choose us"
            subtitle="Boutique comfort, designed for modern travelers"
          />
          <feature-grid :items="hotelHighlights" />
        </div>

        <div class="ion-margin-top ion-margin-bottom">
          <story-section
            :title="hotelStory.title"
            :paragraphs="hotelStory.paragraphs"
            :accent-title="hotelStory.accentTitle"
            :accent-text="hotelStory.accentText"
          />
        </div>

        <div class="ion-margin-top ion-margin-bottom">
          <base-section-title title="Your stay, curated" />
          <feature-grid :items="experienceHighlights" />
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
