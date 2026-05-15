export const toIsoDate = (
  value: string | string[] | null | undefined
): string => {
  if (!value) {
    return ""
  }

  const dateValue = Array.isArray(value) ? value[0] : value
  return dateValue.split("T")[0]
}

const parseDateOnly = (value: string): Date | null => {
  if (!value) {
    return null
  }

  const parsed = new Date(`${value}T00:00:00`)
  return Number.isNaN(parsed.getTime()) ? null : parsed
}

export const getTodayIsoDate = (): string =>
  new Date().toISOString().split("T")[0]

export const formatDate = (value: string, locale = "en-US"): string => {
  const parsed = parseDateOnly(value)
  if (!parsed) {
    return ""
  }

  return new Intl.DateTimeFormat(locale, {
    month: "short",
    day: "2-digit",
    year: "numeric",
  }).format(parsed)
}

export const validateDateRange = (
  checkInDate: string,
  checkOutDate: string
): string | null => {
  if (!checkInDate || !checkOutDate) {
    return "Please select both check-in and check-out dates."
  }

  const checkIn = parseDateOnly(checkInDate)
  const checkOut = parseDateOnly(checkOutDate)

  if (!checkIn || !checkOut) {
    return "Please select valid dates."
  }

  if (checkOut <= checkIn) {
    return "Check-out must be after check-in."
  }

  return null
}
