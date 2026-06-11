export const roomsPageContent = {
  title: "Rooms",
  subtitle: "Choose a space that fits your rhythm in Vienna",
  lead:
    "Explore our boutique rooms and suites, each curated with local design details, restorative textures, and thoughtful extras.",
  emptyTitle: "No rooms to show yet",
  emptyBody:
    "We could not load any rooms right now. Please try again in a moment.",
  emptyAction: "Try again",
  paginationLabel: "Page",
  paginationAriaLabel: "Room pages",
  roomsMeta: (start: number, end: number, total: number) =>
    `Showing ${start}-${end} of ${total} rooms`,
  filter: {
    title: "Find available rooms",
    description:
      "Pick your dates to see only the rooms free for the whole stay.",
    checkInLabel: "Check-in",
    checkOutLabel: "Check-out",
    checkInPlaceholder: "Please choose check-in date",
    checkOutPlaceholder: "Please choose check-out date",
    toggle: "Enter stay dates",
    hide: "Hide",
    apply: "Show available",
    clear: "Clear dates",
    activeLabel: (checkIn: string, checkOut: string) =>
      `Showing rooms available ${checkIn} – ${checkOut}`,
    validationError: "Check-out must be after check-in.",
    emptyTitle: "No rooms available for these dates",
    emptyBody:
      "Try a different date range — every room is booked for the stay you picked.",
  },
}

export const extrasContent = {
  title: "Included extras",
  subtitle: "Thoughtful amenities in every stay",
  emptyMessage: "No extras available right now.",
}
