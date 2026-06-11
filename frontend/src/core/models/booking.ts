import type { Room } from "./room";

export interface BookingRequest {
  roomId: number;
  guestFirstName: string;
  guestLastName: string;
  guestEmail: string;
  confirmEmail: string;
  guestCount: number;
  checkInDate: string;
  checkOutDate: string;
  breakfastIncluded: boolean;
}

/**
 * Context that the booking flow owns outside of the editable guest draft
 * (selected room and date range). Combined with the draft to assemble a
 * complete {@link BookingRequest}.
 */
export interface BookingRequestContext {
  roomId: number;
  checkInDate: string;
  checkOutDate: string;
}

/**
 * Assembles a complete {@link BookingRequest} from the partial guest draft and
 * the surrounding booking context. Normalizes optional draft fields to their
 * required types so callers (components) never build the request by hand.
 */
export const buildBookingRequest = (
  draft: Partial<BookingRequest>,
  context: BookingRequestContext
): BookingRequest => ({
  roomId: context.roomId,
  guestFirstName: draft.guestFirstName ?? "",
  guestLastName: draft.guestLastName ?? "",
  guestEmail: draft.guestEmail ?? "",
  confirmEmail: draft.confirmEmail ?? "",
  guestCount: Number(draft.guestCount ?? 1),
  checkInDate: context.checkInDate,
  checkOutDate: context.checkOutDate,
  breakfastIncluded: Boolean(draft.breakfastIncluded),
});

export interface PriceBreakdown {
  nights: number;
  roomRate: number;
  breakfastRate: number;
  breakfastPerPersonPerDay: number;
}

export interface Guest {
  firstName: string;
  lastName: string;
  email: string;
}

export interface HotelContact {
  name: string;
  street: string;
  city: string;
  postalCode: string;
  country: string;
  email: string;
  phone: string;
  latitude: number;
  longitude: number;
}

export interface Directions {
  byTrain: string;
  byCar: string;
  parking: string;
}

export interface BookingConfirmation {
  bookingId: number;
  checkInDate: string;
  checkOutDate: string;
  breakfastIncluded: boolean;
  guestCount: number;
  totalPrice: number;
  priceBreakdown: PriceBreakdown;
  guest: Guest;
  room: Room;
  hotelContact: HotelContact;
  directions: Directions;
}
