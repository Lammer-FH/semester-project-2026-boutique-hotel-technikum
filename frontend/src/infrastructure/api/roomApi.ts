import type { PaginatedResponse } from "../../core/models/api";
import type { Room } from "../../core/models/room";
import httpClient from "./httpClient";
import type { RoomApi } from "../mappers/roomMapper";
import { mapRoom } from "../mappers/roomMapper";

interface PaginatedResponseApi<T> {
  data: T[];
  pagination: {
    page: number;
    size: number;
    total: number;
    total_pages: number;
  };
}

export const listRooms = async (
  page: number,
  size: number
): Promise<PaginatedResponse<Room>> => {
  const response = await httpClient.get<PaginatedResponseApi<RoomApi>>(
    "/rooms",
    {
      params: { page, size },
    }
  );

  return {
    data: response.data.data.map(mapRoom),
    pagination: {
      page: response.data.pagination.page,
      size: response.data.pagination.size,
      total: response.data.pagination.total,
      totalPages: response.data.pagination.total_pages,
    },
  };
};

export const getRoom = async (roomId: number): Promise<Room> => {
  const response = await httpClient.get<RoomApi>(`/rooms/${roomId}`);
  return mapRoom(response.data);
};
