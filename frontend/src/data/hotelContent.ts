import type { RouteLocationRaw } from "vue-router"

export type FeatureIcon = 'bed' | 'globe' | 'leaf' | 'location' | 'map' | 'person' | 'restaurant' | 'sparkles'

type HeroContent = {
  title: string
  subtitle: string
  primaryLabel: string
  primaryRoute: RouteLocationRaw
  secondaryLabel: string
  secondaryRoute: RouteLocationRaw
}

export const heroContent: HeroContent = {
  title: "Boutique Hotel Technikum",
  subtitle:
    "A calm hideaway in the heart of Vienna, crafted for slow mornings and bold city nights.",
  primaryLabel: "Explore the hotel",
  primaryRoute: { name: "About" },
  secondaryLabel: "About Us",
  secondaryRoute: { name: "About" },
}

export type FeatureItem = {
  key: string
  title: string
  description: string
  icon: FeatureIcon
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

export const contactDetails = {
  phone: "+43 1 234567",
  email: "contact@hotel-technikum.example",
  addressLine1: "Höchstädtplatz 6",
  addressLine2: "1200 Vienna, Austria",
}

// hotelContent.ts

export const imprintDetails = {
  sections: [
    {
      title: "Company Information",
      lines: [
        "Boutique Hotel Technikum GmbH",
        "Höchstädtplatz 6",
        "1200 Vienna, Austria"
      ]
    },
    {
      title: "Contact",
      lines: [
        "Phone: +43 1 234 5678",
        "Email: info@bht.at",
        "Web: www.boutique-hotel-technikum.at"
      ]
    },
    {
      title: "Business Registration",
      lines: [
        "Commercial Register Number: FN 123456a",
        "Commercial Court: Vienna",
        "UID Number: ATU12345678"
      ]
    },
    {
      title: "Management",
      lines: [
        "Managing Directors: Dr. Maria Schmidt, Mag. Thomas Weber"
      ]
    },
    {
      title: "Professional Association",
      lines: [
        "Member of the Austrian Hotel Association",
        "Subject to the Austrian Trade Regulations"
      ]
    },
    {
      title: "Data Protection Officer",
      lines: [
        "Email: datenschutz@bht.at"
      ]
    },
    {
      title: "Disclaimer",
      lines: [
        "The content of this website has been compiled with meticulous care and to the best of our knowledge. However, we cannot assume any liability for the up-to-dateness, completeness or accuracy of any of the pages."
      ]
    },
    {
      title: "Copyright",
      lines: [
        "The content and works published on this website are governed by the copyright laws of Austria. Any duplication, processing, distribution or any form of utilisation beyond the scope of copyright law shall require the prior written consent of the author or authors in question."
      ]
    }
  ]
}
