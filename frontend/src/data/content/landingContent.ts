import type { RouteLocationRaw } from "vue-router"
import type { FeatureItem } from "@/core/models/content"

type HeroContent = {
  title: string
  subtitle: string
  primaryLabel: string
  primaryRoute: RouteLocationRaw
  secondaryLabel: string
  secondaryRoute: RouteLocationRaw
}

type HeroPanelItem = {
  icon: "bed" | "cafe" | "leaf"
  label: string
}

export const heroContent: HeroContent = {
  title: "Boutique Hotel Technikum",
  subtitle:
    "A calm hideaway in the heart of Vienna, crafted for slow mornings and bold city nights.",
  primaryLabel: "Book a room",
  primaryRoute: { name: "RoomsIndex" },
  secondaryLabel: "About Us",
  secondaryRoute: { name: "About" },
}

export const landingContent = {
  highlightsTitle: "Why guests choose us",
  highlightsSubtitle: "Boutique comfort, designed for modern travelers",
  curatedTitle: "Your stay, curated",
}

export const heroBannerContent = {
  eyebrow: "Boutique stay in Vienna",
  panelTitle: "At a glance",
  panelItems: [
    { icon: "bed", label: "28 rooms with custom details" },
    { icon: "cafe", label: "Late breakfast until 11:30" },
    { icon: "leaf", label: "Quiet courtyard lounge" },
  ] satisfies HeroPanelItem[],
}

export const hotelHighlights: FeatureItem[] = [
  {
    key: "sustainability",
    title: "Thoughtful sustainability",
    description: "Solar-powered suites, local suppliers, and zero single-use plastics.",
    icon: "leaf",
  },
  {
    key: "design",
    title: "Design-forward interiors",
    description: "Mid-century textures, handmade ceramics, and soft, quiet lighting.",
    icon: "sparkles",
  },
  {
    key: "comfort",
    title: "Comfort-first sleep",
    description: "Premium linens, blackout curtains, and a custom pillow menu.",
    icon: "bed",
  },
]

export const hotelStory = {
  title: "A boutique stay with a neighborhood soul",
  paragraphs: [
    "Boutique Hotel Technikum is a modern retreat shaped by the rhythm of Vienna. Our spaces celebrate craftsmanship, slow travel, and the small comforts that make a stay memorable.",
    "Every room is curated with warm textures, curated local art, and a sense of calm so you can reset between city adventures.",
  ],
  accentTitle: "What guests love",
  accentText:
    "Quiet rooms, generous breakfast hours, and a lobby that feels like a living room.",
}

export const experienceHighlights: FeatureItem[] = [
  {
    key: "breakfast",
    title: "Market breakfast",
    description: "Seasonal ingredients, Vienna pastries, and slow-roasted coffee.",
    icon: "restaurant",
  },
  {
    key: "location",
    title: "Walkable location",
    description: "Minutes from the Danube, with easy access to the city center.",
    icon: "location",
  },
  {
    key: "concierge",
    title: "Curated concierge",
    description: "Local tips, gallery routes, and hidden courtyards on demand.",
    icon: "map",
  },
]
