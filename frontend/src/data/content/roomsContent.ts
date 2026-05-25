export const roomsPageContent = {
  title: "Rooms",
  subtitle: "Choose a space that fits your rhythm in Vienna",
  lead:
    "Explore our boutique rooms and suites, each curated with local design details, restorative textures, and thoughtful extras.",
  emptyTitle: "No rooms to show yet",
  emptyBody:
    "We could not load any rooms right now. Please try again in a moment.",
  emptyAction: "Try again",
  toastErrorFallback: "We could not load rooms right now.",
  paginationLabel: "Page",
  paginationAriaLabel: "Room pages",
  roomsMeta: (start: number, end: number, total: number) =>
    `Showing ${start}-${end} of ${total} rooms`,
}

export const extrasContent = {
  title: "Included extras",
  subtitle: "Thoughtful amenities in every stay",
  emptyMessage: "No extras available right now.",
}
