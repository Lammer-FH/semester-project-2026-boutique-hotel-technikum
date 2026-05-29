import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from "./constants";

export interface PaginationRequest {
  page: number;
  size: number;
}

export interface PaginationRangeOptions {
  currentPage: number;
  pageSize: number;
  totalItems: number;
  currentCount: number;
}

export const normalizePageNumber = (
  page: number,
  fallback = DEFAULT_PAGE
): number => {
  const parsed = Number(page);
  return Number.isInteger(parsed) && parsed > 0 ? parsed : fallback;
};

export const normalizePageSize = (
  size: number,
  fallback = DEFAULT_PAGE_SIZE
): number => {
  const parsed = Number(size);
  return Number.isInteger(parsed) && parsed > 0 ? parsed : fallback;
};

export const resolvePaginationRequest = (
  request: Partial<PaginationRequest> | undefined,
  current: PaginationRequest
): PaginationRequest => ({
  page: normalizePageNumber(request?.page ?? current.page, current.page),
  size: normalizePageSize(request?.size ?? current.size, current.size),
});

export const toPaginationQueryParams = (
  page: number,
  size: number
): PaginationRequest => ({
  page: normalizePageNumber(page),
  size: normalizePageSize(size),
});

export const toPaginationCacheKey = (page: number, size: number): string =>
  `${page}-${size}`;

export const buildPaginationPages = (totalPages: number): number[] => {
  const safeTotalPages = Math.max(1, Math.floor(Number(totalPages) || 1));
  return Array.from({ length: safeTotalPages }, (_, index) => index + 1);
};

export const getPaginationRange = ({
  currentPage,
  pageSize,
  totalItems,
  currentCount,
}: PaginationRangeOptions): { start: number; end: number } => {
  if (currentCount <= 0) {
    return { start: 0, end: 0 };
  }

  const safePage = normalizePageNumber(currentPage);
  const safeSize = normalizePageSize(pageSize);
  const start = (safePage - 1) * safeSize + 1;
  const end = Math.min(safePage * safeSize, Math.max(0, totalItems));

  return { start, end };
};

export const shouldSkipPageChange = (
  nextPage: number,
  currentPage: number,
  hasError: boolean
): boolean => nextPage === currentPage && !hasError;