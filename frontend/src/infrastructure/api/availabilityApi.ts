import httpClient from "./httpClient";
import type { AvailabilityResult } from "../../core/models/availability";
import type { AvailabilityResponseApi } from "../mappers/apiMapper";
import { mapAvailabilityResponse } from "../mappers/apiMapper";

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

  return mapAvailabilityResponse(response.data);
};
