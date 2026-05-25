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
