import type {
  BookingConfirmation,
  BookingRequest,
} from "../../core/models/booking";
import httpClient from "./httpClient";
import {
  mapBookingConfirmation,
  toBookingRequestApi,
} from "../mappers/bookingMapper";
import type { BookingResponseApi } from "../mappers/bookingMapper";

const createIdempotencyKey = () =>
  `booking-${Date.now()}-${Math.random().toString(16).slice(2)}`;

export const createBooking = async (
  request: BookingRequest
): Promise<BookingConfirmation> => {
  const response = await httpClient.post<BookingResponseApi>(
    "/bookings",
    toBookingRequestApi(request),
    {
      headers: {
        "Idempotency-Key": createIdempotencyKey(),
      },
    }
  );

  return mapBookingConfirmation(response.data);
};

export const getBooking = async (
  bookingId: number
): Promise<BookingConfirmation> => {
  const response = await httpClient.get<BookingResponseApi>(
    `/bookings/${bookingId}`
  );

  return mapBookingConfirmation(response.data);
};
