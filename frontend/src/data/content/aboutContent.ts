import type { FeatureItem } from "@/core/models/content"

export const aboutContent = {
  title: "Our story",
  paragraphs: [
    "We opened Boutique Hotel Technikum to create a place where calm meets curiosity. Our team comes from hospitality, design, and culinary backgrounds, and we designed every detail to feel intentional and personal.",
    "The hotel lives in the creative energy of Brigittenau. We collaborate with nearby studios, small roasters, and makers to keep the experience local and authentic.",
  ],
  accentTitle: "Behind the scenes",
  accentText:
    "A tight-knit team shapes every detail, from room rituals to local partnerships.",
}

export const aboutPageContent = {
  valuesTitle: "Our values",
  valuesSubtitle: "What guides every stay",
  neighborhoodTitle: "Neighborhood highlights",
  neighborhoodSubtitle: "Creative Brigittenau, just outside the center",
}

export const aboutValues: FeatureItem[] = [
  {
    key: "human",
    title: "Human-scale hospitality",
    description: "We keep our rooms limited so every guest receives attention to detail.",
    icon: "person",
  },
  {
    key: "sustainable",
    title: "Sustainable comfort",
    description: "Energy-smart systems and thoughtful sourcing reduce our footprint.",
    icon: "leaf",
  },
  {
    key: "local",
    title: "Local culture",
    description: "From art to dining, we showcase Vienna through a local lens.",
    icon: "globe",
  },
]

export const neighborhoodHighlights: FeatureItem[] = [
  {
    key: "danube",
    title: "Danube mornings",
    description: "Start your day with a riverside walk and a coffee from our partner roaster.",
    icon: "location",
  },
  {
    key: "gallery",
    title: "Gallery route",
    description: "Discover independent galleries and small design studios within 10 minutes on foot.",
    icon: "map",
  },
  {
    key: "market",
    title: "Market evenings",
    description: "Head to the market hall for seasonal produce and cozy wine bars.",
    icon: "restaurant",
  },
]
