package at.fhtw.hotel.dto.request;

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
public class BookingRequest {

    @NotNull
    Long roomId;

    @NotBlank
    String guestFirstName;

    @NotBlank
    String guestLastName;

    @NotBlank
    @Email
    String guestEmail;

    @NotBlank
    @Email
    String confirmEmail;

    @Min(1)
    int guestCount;

    @NotNull
    @FutureOrPresent
    LocalDate checkInDate;

    @NotNull
    @FutureOrPresent
    LocalDate checkOutDate;

    boolean breakfastIncluded;
}
