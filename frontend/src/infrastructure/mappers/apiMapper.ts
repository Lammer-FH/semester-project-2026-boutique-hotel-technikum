import type { AvailabilityResult } from "../../core/models/availability";
import type { PaginatedResponse } from "../../core/models/api";

export interface PaginatedResponseApi<T> {
  data: T[];
  pagination: {
    page: number;
    size: number;
    total: number;
    total_pages: number;
  };
}

export const mapPaginatedResponse = <TApi, TModel>(
  api: PaginatedResponseApi<TApi>,
  mapItem: (item: TApi) => TModel
): PaginatedResponse<TModel> => ({
  data: api.data.map(mapItem),
  pagination: {
    page: api.pagination.page,
    size: api.pagination.size,
    total: api.pagination.total,
    totalPages: api.pagination.total_pages,
  },
});

export const mapListResponse = <TApi, TModel>(
  api: TApi[],
  mapItem: (item: TApi) => TModel
): TModel[] => api.map(mapItem);

export interface AvailabilityResponseApi {
  room_id: number;
  available: boolean;
  message: string;
}

export const mapAvailabilityResponse = (
  api: AvailabilityResponseApi,
  checkInDate: string,
  checkOutDate: string
): AvailabilityResult => ({
  roomId: api.room_id,
  available: api.available,
  message: api.message,
  checkInDate,
  checkOutDate,
});
