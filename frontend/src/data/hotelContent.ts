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
  primaryLabel: "Book a room",
  primaryRoute: { name: "RoomsIndex" },
  secondaryLabel: "About Us",
  secondaryRoute: { name: "About" },
}

export const navigationContent = {
  menuToggleLabel: "Toggle navigation",
  brandAlt: "Boutique Hotel Technikum",
  homeLabel: "Home",
  roomsLabel: "Rooms",
  aboutLabel: "About",
  primaryNavLabel: "Primary",
  mobileNavLabel: "Mobile",
}

export const footerContent = {
  brandName: "Boutique Hotel Technikum",
  location: "Vienna, Austria",
  imprintLabel: "Imprint",
  tagline: "A calm stay designed for curious travelers.",
}

export const landingContent = {
  highlightsTitle: "Why guests choose us",
  highlightsSubtitle: "Boutique comfort, designed for modern travelers",
  curatedTitle: "Your stay, curated",
}

export const roomsPageContent = {
  title: "Rooms",
  subtitle: "Choose a space that fits your rhythm in Vienna",
  lead:
    "Explore our boutique rooms and suites, each curated with local design details, restorative textures, and thoughtful extras.",
  paginationLabel: "Page",
  paginationAriaLabel: "Room pages",
  roomsMeta: (start: number, end: number, total: number) =>
    `Showing ${start}-${end} of ${total} rooms`,
}

export const aboutPageContent = {
  valuesTitle: "Our values",
  valuesSubtitle: "What guides every stay",
  neighborhoodTitle: "Neighborhood highlights",
  neighborhoodSubtitle: "Creative Brigittenau, just outside the center",
}

export const imprintPageContent = {
  title: "Imprint",
  subtitle: "Legal information",
}

export const heroBannerContent = {
  eyebrow: "Boutique stay in Vienna",
  panelTitle: "At a glance",
  panelItems: [
    "28 rooms with custom details",
    "Late breakfast until 11:30",
    "Quiet courtyard lounge",
  ],
}

export const contactStripContent = {
  title: "Ready for your stay?",
  emailCta: "Send us an email",
  phoneCta: "Call us",
}

export const availabilityDialogContent = {
  triggerLabel: "Check availability",
  title: "Check availability",
  checkInLabel: "Check-in",
  checkOutLabel: "Check-out",
  checkingLabel: "Checking...",
  confirmLabel: "Check availability",
  availableLabel: "Available",
  unavailableLabel: "Not available",
  bookNowLabel: "Book now",
  unavailableMessage: "Unable to check availability.",
  closeLabel: "Close",
}

export const bookingDialogContent = {
  eyebrow: "Booking details",
  stepLabel: {
    details: "Step 1 of 2",
    review: "Step 2 of 2",
  },
  stepTitle: {
    details: "Guest details",
    review: "Review your booking",
  },
  hints: {
    details: "We will send the confirmation to the email below.",
    review: "Please confirm the details below.",
  },
  fields: {
    firstName: "First name",
    lastName: "Last name",
    email: "Email",
    confirmEmail: "Confirm email",
    guests: "Guests",
    breakfast: "Breakfast included",
  },
  placeholders: {
    firstName: "e.g. Julia",
    lastName: "e.g. Steiner",
    email: "name@email.com",
    confirmEmail: "Repeat email",
  },
  helpers: {
    maxGuests: (maxGuests: number) => `Max ${maxGuests} guests in this room.`,
  },
  reviewLabels: {
    leadGuest: "Lead guest",
    email: "Email",
    guests: "Guests",
    breakfast: "Breakfast",
    included: "Included",
    notIncluded: "Not included",
  },
  buttons: {
    review: "Review booking",
    back: "Back",
    confirm: "Confirm booking",
    confirming: "Booking...",
    done: "Done",
    close: "Close",
  },
  confirmation: {
    title: "Your booking is confirmed",
    bookingId: (id: number) => `Booking ID: ${id}`,
  },
  errors: {
    nameMissing: "Please enter the lead guest name.",
    emailMissing: "Please enter and confirm the email address.",
    emailMismatch: "Email addresses do not match.",
    guestCountInvalid: "Please select a valid guest count.",
    maxGuestsExceeded: (maxGuests: number) =>
      `This room allows up to ${maxGuests} guests.`,
  },
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
