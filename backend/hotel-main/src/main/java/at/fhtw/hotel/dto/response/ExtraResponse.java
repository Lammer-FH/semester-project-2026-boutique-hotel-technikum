package at.fhtw.hotel.dto.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ExtraResponse {
    Long id;
    String code;
    String title;
    String description;
    String iconName;
}
