import type { Extra } from "../../core/models/room";
import httpClient from "./httpClient";
import type { ExtraApi } from "../mappers/roomMapper";
import { mapExtra } from "../mappers/roomMapper";
import { mapListResponse } from "../mappers/apiMapper";

export const listExtras = async (): Promise<Extra[]> => {
  const response = await httpClient.get<ExtraApi[]>("/extras");
  return mapListResponse(response.data, mapExtra);
};
