export type FeatureIcon =
  | "bed"
  | "globe"
  | "leaf"
  | "location"
  | "map"
  | "person"
  | "restaurant"
  | "sparkles"

export type FeatureItem = {
  key: string
  title: string
  description: string
  icon: FeatureIcon
}
