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
  changeDatesLabel: "Change dates",
  unavailableMessage: "Unable to check availability.",
  closeLabel: "Close",
  result: {
    selectedDatesLabel: "Selected dates",
    availableHint: "You can continue with the booking now.",
    unavailableHint: "Try different dates or check another room.",
  },
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
    firstName: "e.g. Max",
    lastName: "e.g. Mustermann",
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
  reviewSummary: {
    summarySection: "Guest & stay details",
    guestSection: "Guest details",
    staySection: "Stay details",
    note: "You can still change details before confirming.",
  },
  buttons: {
    review: "Review booking",
    back: "Back",
    changeDates: "Change dates",
    confirm: "Confirm booking",
    confirming: "Booking...",
    done: "Done",
    close: "Close",
  },
  confirmation: {
    title: "Your booking is confirmed",
    subtitle: "Reservation saved and emailed to you.",
    bookingIdLabel: "Booking ID",
    stayLabel: "Stay",
    nextStepLabel: "Next step",
    nextStepText: "Bring the booking ID to check-in.",
    bookingId: (id: number) => `Booking ID: ${id}`,
  },
  errors: {
    nameMissing: "Please enter the guest name.",
    emailMissing: "Please enter and confirm the email address.",
    emailMismatch: "Email addresses do not match.",
    guestCountInvalid: "Please select a valid guest count.",
    maxGuestsExceeded: (maxGuests: number) =>
      `This room allows up to ${maxGuests} guests.`,
  },
}
