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
} from "ionicons/icons"

const iconMap: Record<string, string> = {
  accessible: accessibilityOutline,
  balcony: homeOutline,
  bed: bedOutline,
  breakfast: cafeOutline,
  minibar: wineOutline,
  spa: leafOutline,
  view: sunnyOutline,
  wifi: wifiOutline,
  workspace: briefcaseOutline,
}

export const resolveExtraIcon = (iconName: string) => iconMap[iconName] ?? sparklesOutline
