package at.fhtw.hotel.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Request parameters for checking room availability")
public class AvailabilityRequest {

    @Schema(description = "Check-in date (ISO 8601)", example = "2026-06-15")
    @NotNull
    @FutureOrPresent
    LocalDate checkInDate;

    @Schema(description = "Check-out date (ISO 8601)", example = "2026-06-18")
    @NotNull
    @FutureOrPresent
    LocalDate checkOutDate;
}
