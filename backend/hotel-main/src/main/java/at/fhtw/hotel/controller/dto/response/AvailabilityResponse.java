package at.fhtw.hotel.controller.dto.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AvailabilityResponse {
    Long roomId;
    boolean available;
    String message;
}
