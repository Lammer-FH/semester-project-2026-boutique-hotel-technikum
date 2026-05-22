import type { Extra } from "../../core/models/room";
import httpClient from "./httpClient";
import type { ExtraApi } from "../mappers/roomMapper";
import { mapExtra } from "../mappers/roomMapper";

export const listExtras = async (): Promise<Extra[]> => {
  const response = await httpClient.get<ExtraApi[]>("/extras");
  return response.data.map(mapExtra);
};
