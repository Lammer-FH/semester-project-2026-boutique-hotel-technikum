import type { PaginatedResponse } from "../../core/models/api";
import type { Room } from "../../core/models/room";
import httpClient from "./httpClient";
import type { RoomApi } from "../mappers/roomMapper";
import { mapRoom } from "../mappers/roomMapper";
import type { PaginatedResponseApi } from "../mappers/apiMapper";
import { mapPaginatedResponse } from "../mappers/apiMapper";
import { toPaginationQueryParams } from "../../core/pagination";

export interface RoomDateFilter {
  checkInDate?: string;
  checkOutDate?: string;
}

export const listRooms = async (
  page: number,
  size: number,
  signal?: AbortSignal,
  dateFilter?: RoomDateFilter
): Promise<PaginatedResponse<Room>> => {
  const params: Record<string, string | number> = {
    ...toPaginationQueryParams(page, size),
  };

  if (dateFilter?.checkInDate && dateFilter?.checkOutDate) {
    params.check_in_date = dateFilter.checkInDate;
    params.check_out_date = dateFilter.checkOutDate;
  }

  const response = await httpClient.get<PaginatedResponseApi<RoomApi>>(
    "/rooms",
    {
      params,
      signal,
    }
  );

  return mapPaginatedResponse(response.data, mapRoom);
};

export const getRoom = async (roomId: number): Promise<Room> => {
  const response = await httpClient.get<RoomApi>(`/rooms/${roomId}`);
  return mapRoom(response.data);
};
