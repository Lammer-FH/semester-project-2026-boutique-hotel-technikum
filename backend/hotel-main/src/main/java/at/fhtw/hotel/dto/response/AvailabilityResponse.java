package at.fhtw.hotel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Result of a room availability check")
public class AvailabilityResponse {
    @Schema(description = "The room ID that was checked", example = "1")
    Long roomId;

    @Schema(description = "Whether the room is available for the requested dates", example = "true")
    boolean isAvailable;

    @Schema(description = "Human-readable availability message", example = "Room is available")
    String message;
}
