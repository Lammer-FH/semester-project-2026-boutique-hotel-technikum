import type { PaginatedResponse } from "../../core/models/api";
import type { Room } from "../../core/models/room";
import httpClient from "./httpClient";
import type { RoomApi } from "../mappers/roomMapper";
import { mapRoom } from "../mappers/roomMapper";
import type { PaginatedResponseApi } from "../mappers/apiMapper";
import { mapPaginatedResponse } from "../mappers/apiMapper";

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

  return mapPaginatedResponse(response.data, mapRoom);
};

export const getRoom = async (roomId: number): Promise<Room> => {
  const response = await httpClient.get<RoomApi>(`/rooms/${roomId}`);
  return mapRoom(response.data);
};
