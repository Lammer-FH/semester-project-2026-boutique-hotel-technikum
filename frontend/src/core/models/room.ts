export interface RoomImage {
  id: number;
  url: string;
  sortOrder: number;
  altText: string;
}

export interface Extra {
  id: number;
  code: string;
  title: string;
  description: string;
  iconName: string;
}

export interface Room {
  id: number;
  title: string;
  description: string;
  maxGuests: number;
  basePricePerNight: number;
  images: RoomImage[];
  extras: Extra[];
}
