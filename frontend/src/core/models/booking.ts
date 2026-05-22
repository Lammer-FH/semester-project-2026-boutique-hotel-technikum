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
  totalPrice: number;
  priceBreakdown: PriceBreakdown;
  guest: Guest;
  room: Room;
  hotelContact: HotelContact;
  directions: Directions;
}
