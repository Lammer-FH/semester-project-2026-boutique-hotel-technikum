import type { Extra, Room, RoomImage } from "../../core/models/room";

export interface RoomImageApi {
  id: number;
  url: string;
  sort_order: number;
  alt_text: string;
}

export interface ExtraApi {
  id: number;
  code: string;
  title: string;
  description: string;
  icon_name: string;
}

export interface RoomApi {
  id: number;
  title: string;
  description: string;
  max_guests: number;
  base_price_per_night: number;
  images: RoomImageApi[];
  extras: ExtraApi[];
}

export const mapRoomImage = (image: RoomImageApi): RoomImage => ({
  id: image.id,
  url: image.url,
  sortOrder: image.sort_order,
  altText: image.alt_text,
});

export const mapExtra = (extra: ExtraApi): Extra => ({
  id: extra.id,
  code: extra.code,
  title: extra.title,
  description: extra.description,
  iconName: extra.icon_name,
});

export const mapRoom = (room: RoomApi): Room => ({
  id: room.id,
  title: room.title,
  description: room.description,
  maxGuests: room.max_guests,
  basePricePerNight: room.base_price_per_night,
  images: room.images.map(mapRoomImage),
  extras: room.extras.map(mapExtra),
});
