package at.fhtw.hotel.controller.dto.request;

import at.fhtw.hotel.controller.dto.annotation.EmailMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@EmailMatch
@Schema(description = "Request payload for creating a new booking")
public class BookingRequest {

    @Schema(description = "ID of the room to book", example = "1")
    @NotNull
    Long roomId;

    @Schema(description = "Guest's first name", example = "John")
    @NotBlank
    String guestFirstName;

    @Schema(description = "Guest's last name", example = "Doe")
    @NotBlank
    String guestLastName;

    @Schema(description = "Guest's email address", example = "john.doe@example.com")
    @NotBlank
    @Email
    String guestEmail;

    @Schema(description = "Email confirmation (must match guestEmail)", example = "john.doe@example.com")
    @NotBlank
    @Email
    String confirmEmail;

    @Schema(description = "Number of guests", example = "2")
    @Min(1)
    int guestCount;

    @Schema(description = "Check-in date (ISO 8601)", example = "2026-06-15")
    @NotNull
    @FutureOrPresent
    LocalDate checkInDate;

    @Schema(description = "Check-out date (ISO 8601)", example = "2026-06-18")
    @NotNull
    @FutureOrPresent
    LocalDate checkOutDate;

    @Schema(description = "Whether to include breakfast", example = "true")
    boolean breakfastIncluded;
}
