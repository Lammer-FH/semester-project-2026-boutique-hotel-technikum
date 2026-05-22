import type { AvailabilityResult } from "../../core/models/availability";
import httpClient from "./httpClient";

interface AvailabilityResponseApi {
  room_id: number;
  available: boolean;
  message: string;
}

export const checkAvailability = async (
  roomId: number,
  checkInDate: string,
  checkOutDate: string
): Promise<AvailabilityResult> => {
  const response = await httpClient.get<AvailabilityResponseApi>(
    `/rooms/${roomId}/availability`,
    {
      params: {
        check_in_date: checkInDate,
        check_out_date: checkOutDate,
      },
    }
  );

  return {
    roomId: response.data.room_id,
    available: response.data.available,
    message: response.data.message,
  };
};
