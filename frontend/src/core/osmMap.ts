export const OSM_ATTRIBUTION_TEXT = "Map data (c) OpenStreetMap contributors";
export const OSM_ATTRIBUTION_URL = "https://www.openstreetmap.org/copyright";

export const DEFAULT_HOTEL_COORDINATES = {
  latitude: 48.23924,
  longitude: 16.37739,
};

export interface OsmMapTile {
  key: string;
  url: string;
}

const toFiniteCoordinate = (value: number | null | undefined, fallback: number): number =>
  Number.isFinite(value) ? Number(value) : fallback;

const clamp = (value: number, min: number, max: number): number =>
  Math.min(Math.max(value, min), max);

const longitudeToTileX = (longitude: number | null | undefined, zoom: number): number => {
  const safeLongitude = toFiniteCoordinate(longitude, DEFAULT_HOTEL_COORDINATES.longitude);
  const normalizedLongitude = clamp(safeLongitude, -180, 180);
  return Math.floor(((normalizedLongitude + 180) / 360) * 2 ** zoom);
};

const latitudeToTileY = (latitude: number | null | undefined, zoom: number): number => {
  const safeLatitude = toFiniteCoordinate(latitude, DEFAULT_HOTEL_COORDINATES.latitude);
  const normalizedLatitude = clamp(safeLatitude, -85.05112878, 85.05112878);
  const radians = (normalizedLatitude * Math.PI) / 180;
  return Math.floor(
    ((1 - Math.log(Math.tan(radians) + 1 / Math.cos(radians)) / Math.PI) / 2) *
      2 ** zoom
  );
};

export const buildOsmTileUrl = (
  x: number,
  y: number,
  zoom: number
): string => `https://tile.openstreetmap.org/${zoom}/${x}/${y}.png`;

export const buildOsmTileGrid = (
  latitude: number | null | undefined,
  longitude: number | null | undefined,
  zoom = 16,
  columns = 3,
  rows = 2
): OsmMapTile[] => {
  const centerX = longitudeToTileX(longitude, zoom);
  const centerY = latitudeToTileY(latitude, zoom);
  const xStart = centerX - Math.floor(columns / 2);
  const yStart = centerY - Math.floor(rows / 2);
  const maxTile = 2 ** zoom - 1;
  const tiles: OsmMapTile[] = [];

  for (let row = 0; row < rows; row += 1) {
    for (let column = 0; column < columns; column += 1) {
      const x = clamp(xStart + column, 0, maxTile);
      const y = clamp(yStart + row, 0, maxTile);
      tiles.push({
        key: `${zoom}-${x}-${y}`,
        url: buildOsmTileUrl(x, y, zoom),
      });
    }
  }

  return tiles;
};

export const buildStaticOsmMapUrl = (
  latitude: number | null | undefined,
  longitude: number | null | undefined,
  zoom = 16
): string => {
  const x = longitudeToTileX(longitude, zoom);
  const y = latitudeToTileY(latitude, zoom);
  return buildOsmTileUrl(x, y, zoom);
};
