package at.fhtw.hotel.dto.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AvailabilityResponse {
    Long roomId;
    boolean isAvailable;
    String message;
}
