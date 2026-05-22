export const formatPrice = (value: number, locale = "en-US"): string => {
  return new Intl.NumberFormat(locale).format(value)
}
