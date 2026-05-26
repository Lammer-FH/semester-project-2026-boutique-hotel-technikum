import {
  accessibilityOutline,
  bedOutline,
  briefcaseOutline,
  cafeOutline,
  homeOutline,
  leafOutline,
  sparklesOutline,
  sunnyOutline,
  wifiOutline,
  wineOutline,
  carOutline,
  pawOutline,
  laptopOutline,
} from "ionicons/icons"

const iconMap: Record<string, string> = {
  accessible: accessibilityOutline,
  balcony: homeOutline,
  bed: bedOutline,
  breakfast: cafeOutline,
  coffee: cafeOutline,
  minibar: wineOutline,
  wine: wineOutline,
  spa: leafOutline,
  sparkles: sparklesOutline,
  view: sunnyOutline,
  sunny: sunnyOutline,
  wifi: wifiOutline,
  workspace: laptopOutline,
  briefcase: briefcaseOutline,
  parking: carOutline,
  car: carOutline,
  paw: pawOutline,
}

export const resolveExtraIcon = (iconName: string) => iconMap[iconName] ?? sparklesOutline
