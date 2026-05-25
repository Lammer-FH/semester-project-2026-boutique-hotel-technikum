<script setup lang="ts">
import HeroBanner from "@/components/molecules/HeroBanner.vue"
import SectionBlock from "@/components/molecules/SectionBlock.vue"
import FeatureGrid from "@/components/organisms/FeatureGrid.vue"
import StorySection from "@/components/organisms/StorySection.vue"
import ContactStrip from "@/components/organisms/ContactStrip.vue"
import PageLayout from "@/components/layout/PageLayout.vue"
import {
  experienceHighlights,
  heroContent,
  hotelHighlights,
  hotelStory,
  landingContent,
} from "@/data/content/landingContent"
import { contactDetails } from "@/data/content/contactContent"
</script>

<template>
  <page-layout>
        <hero-banner
          :title="heroContent.title"
          :subtitle="heroContent.subtitle"
          :primary-label="heroContent.primaryLabel"
          :primary-route="heroContent.primaryRoute"
          :secondary-label="heroContent.secondaryLabel"
          :secondary-route="heroContent.secondaryRoute"
        />

        <section-block
          :title="landingContent.highlightsTitle"
          :subtitle="landingContent.highlightsSubtitle"
        >
          <feature-grid :items="hotelHighlights" />
        </section-block>

        <section-block>
          <story-section
            :title="hotelStory.title"
            :paragraphs="hotelStory.paragraphs"
            :accent-title="hotelStory.accentTitle"
            :accent-text="hotelStory.accentText"
          />
        </section-block>

        <section-block :title="landingContent.curatedTitle">
          <feature-grid :items="experienceHighlights" />
        </section-block>

        <contact-strip
          :phone="contactDetails.phone"
          :email="contactDetails.email"
          :address-line1="contactDetails.addressLine1"
          :address-line2="contactDetails.addressLine2"
        />
  </page-layout>
</template>
