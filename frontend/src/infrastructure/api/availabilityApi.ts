import httpClient from "./httpClient";
import type { AvailabilityResult } from "../../core/models/availability";
import type { AvailabilityResponseApi } from "../mappers/apiMapper";
import { mapAvailabilityResponse } from "../mappers/apiMapper";

export const checkAvailability = async (
  roomId: number,
  checkInDate: string,
  checkOutDate: string,
  signal?: AbortSignal
): Promise<AvailabilityResult> => {
  const response = await httpClient.get<AvailabilityResponseApi>(
    `/rooms/${roomId}/availability`,
    {
      params: {
        check_in_date: checkInDate,
        check_out_date: checkOutDate,
      },
      signal,
    }
  );

  return mapAvailabilityResponse(response.data, checkInDate, checkOutDate);
};
