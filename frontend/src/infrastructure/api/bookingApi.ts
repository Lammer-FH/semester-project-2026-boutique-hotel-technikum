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

const stableIdempotencyKey = (request: BookingRequest): string => {
  const payload = [
    request.roomId,
    request.checkInDate,
    request.checkOutDate,
    request.guestFirstName,
    request.guestLastName,
    request.guestEmail,
    request.guestCount,
    request.breakfastIncluded,
  ].join("|");
  let hash = 0;
  for (let i = 0; i < payload.length; i++) {
    hash = ((hash << 5) - hash + payload.charCodeAt(i)) | 0;
  }
  return `booking-${Math.abs(hash).toString(36)}`;
};

export const createBooking = async (
  request: BookingRequest
): Promise<BookingConfirmation> => {
  const response = await httpClient.post<BookingResponseApi>(
    "/bookings",
    toBookingRequestApi(request),
    {
      headers: {
        "Idempotency-Key": stableIdempotencyKey(request),
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
