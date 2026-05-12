package at.fhtw.hotel.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AvailabilityRequest {

    @NotNull
    @FutureOrPresent
    LocalDate checkInDate;

    @NotNull
    @FutureOrPresent
    LocalDate checkOutDate;
}
