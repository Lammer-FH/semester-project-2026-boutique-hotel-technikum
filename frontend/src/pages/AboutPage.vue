<script setup lang="ts">
import StorySection from "@/components/organisms/StorySection.vue"
import FeatureGrid from "@/components/organisms/FeatureGrid.vue"
import ContactStrip from "@/components/organisms/ContactStrip.vue"
import PageLayout from "@/components/layout/PageLayout.vue"
import SectionBlock from "@/components/molecules/SectionBlock.vue"
import {
  aboutContent,
  aboutPageContent,
  aboutValues,
  neighborhoodHighlights,
} from "@/data/content/aboutContent"
import { contactDetails } from "@/data/content/contactContent"
</script>

<template>
  <page-layout>
        <section-block>
          <story-section
            :title="aboutContent.title"
            :paragraphs="aboutContent.paragraphs"
            :accent-title="aboutContent.accentTitle"
            :accent-text="aboutContent.accentText"
          />
        </section-block>

        <section-block
          :title="aboutPageContent.valuesTitle"
          :subtitle="aboutPageContent.valuesSubtitle"
        >
          <feature-grid :items="aboutValues" />
        </section-block>

        <section-block
          :title="aboutPageContent.neighborhoodTitle"
          :subtitle="aboutPageContent.neighborhoodSubtitle"
        >
          <feature-grid :items="neighborhoodHighlights" />
        </section-block>

        <contact-strip
          :phone="contactDetails.phone"
          :email="contactDetails.email"
          :address-line1="contactDetails.addressLine1"
          :address-line2="contactDetails.addressLine2"
        />
  </page-layout>
</template>
