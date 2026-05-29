import { isApiError } from "../infrastructure/api/httpClient";

export const toErrorMessage = (error: unknown): string => {
  if (isApiError(error)) {
    const suffix = error.status ? ` (HTTP ${error.status})` : "";
    return `${error.message}${suffix}`;
  }

  if (error instanceof Error) {
    return error.message;
  }

  return "Unexpected error";
};
