import type {
  BookingConfirmation,
  BookingRequest,
  Directions,
  Guest,
  HotelContact,
  PriceBreakdown,
} from "../../core/models/booking";
import type { RoomApi } from "./roomMapper";
import { mapRoom } from "./roomMapper";

export interface BookingRequestApi {
  room_id: number;
  guest_first_name: string;
  guest_last_name: string;
  guest_email: string;
  confirm_email: string;
  guest_count: number;
  check_in_date: string;
  check_out_date: string;
  breakfast_included: boolean;
}

export interface BookingResponseApi {
  booking_id: number;
  check_in_date: string;
  check_out_date: string;
  breakfast_included: boolean;
  total_price: number;
  price_breakdown: {
    nights: number;
    room_rate: number;
    breakfast_rate: number;
    breakfast_per_person_per_day: number;
  };
  guest: {
    first_name: string;
    last_name: string;
    email: string;
  };
  room: RoomApi;
  hotel_contact: {
    name: string;
    street: string;
    city: string;
    postal_code: string;
    country: string;
    email: string;
    phone: string;
  };
  directions: {
    by_train: string;
    by_car: string;
    parking: string;
  };
}

export const toBookingRequestApi = (
  request: BookingRequest
): BookingRequestApi => ({
  room_id: request.roomId,
  guest_first_name: request.guestFirstName,
  guest_last_name: request.guestLastName,
  guest_email: request.guestEmail,
  confirm_email: request.confirmEmail,
  guest_count: request.guestCount,
  check_in_date: request.checkInDate,
  check_out_date: request.checkOutDate,
  breakfast_included: request.breakfastIncluded,
});

const mapPriceBreakdown = (
  api: BookingResponseApi["price_breakdown"]
): PriceBreakdown => ({
  nights: api.nights,
  roomRate: api.room_rate,
  breakfastRate: api.breakfast_rate,
  breakfastPerPersonPerDay: api.breakfast_per_person_per_day,
});

const mapGuest = (api: BookingResponseApi["guest"]): Guest => ({
  firstName: api.first_name,
  lastName: api.last_name,
  email: api.email,
});

const mapHotelContact = (
  api: BookingResponseApi["hotel_contact"]
): HotelContact => ({
  name: api.name,
  street: api.street,
  city: api.city,
  postalCode: api.postal_code,
  country: api.country,
  email: api.email,
  phone: api.phone,
});

const mapDirections = (
  api: BookingResponseApi["directions"]
): Directions => ({
  byTrain: api.by_train,
  byCar: api.by_car,
  parking: api.parking,
});

export const mapBookingConfirmation = (
  api: BookingResponseApi
): BookingConfirmation => ({
  bookingId: api.booking_id,
  checkInDate: api.check_in_date,
  checkOutDate: api.check_out_date,
  breakfastIncluded: api.breakfast_included,
  totalPrice: api.total_price,
  priceBreakdown: mapPriceBreakdown(api.price_breakdown),
  guest: mapGuest(api.guest),
  room: mapRoom(api.room),
  hotelContact: mapHotelContact(api.hotel_contact),
  directions: mapDirections(api.directions),
});
