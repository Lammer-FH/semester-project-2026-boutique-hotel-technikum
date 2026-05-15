import axios, { AxiosError } from "axios";
import type { ApiErrorDetail, ApiErrorPayload } from "../../core/models/api";
import { API_BASE_URL } from "../../config";

export class ApiError extends Error {
  code: string;
  details?: ApiErrorDetail[];
  status?: number;

  constructor(message: string, code: string, details?: ApiErrorDetail[], status?: number) {
    super(message);
    this.name = "ApiError";
    this.code = code;
    this.details = details;
    this.status = status;
  }
}

const httpClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

const normalizeError = (error: AxiosError): ApiError => {
  if (!error.response) {
    return new ApiError(error.message || "Network error", "NETWORK_ERROR");
  }

  const data = error.response.data as Partial<ApiErrorPayload> | undefined;
  const message = data?.error?.message ?? error.message ?? "Request failed";
  const code = data?.error?.code ?? "UNKNOWN_ERROR";
  const details = data?.error?.details;

  return new ApiError(message, code, details, error.response.status);
};

httpClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => Promise.reject(normalizeError(error))
);

export const isApiError = (error: unknown): error is ApiError =>
  error instanceof ApiError;

export default httpClient;
