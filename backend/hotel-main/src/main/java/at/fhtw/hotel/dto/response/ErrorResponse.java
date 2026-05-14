package at.fhtw.hotel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Standard error response body")
public class ErrorResponse {
    @Schema(description = "Error payload")
    ErrorPayload error;

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "Error details")
    public static class ErrorPayload {
        @Schema(description = "Human-readable error message", example = "Room not found")
        String message;

        @Schema(description = "Machine-readable error code", example = "ROOM_NOT_FOUND")
        String code;

        @Schema(description = "List of field-level validation errors (if applicable)")
        List<ErrorDetail> details;
    }

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "A single field-level validation error")
    public static class ErrorDetail {
        @Schema(description = "The field that failed validation", example = "guestEmail")
        String field;

        @Schema(description = "Validation error message", example = "must be a well-formed email address")
        String message;
    }

}
