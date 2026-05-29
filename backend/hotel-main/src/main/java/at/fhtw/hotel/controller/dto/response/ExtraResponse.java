package at.fhtw.hotel.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Bookable extra or amenity")
public class ExtraResponse {
    @Schema(description = "Unique extra identifier", example = "1")
    Long id;

    @Schema(description = "Machine-readable code", example = "wifi")
    String code;

    @Schema(description = "Display title", example = "Free WiFi")
    String title;

    @Schema(description = "Detailed description", example = "Complimentary high-speed WiFi throughout the hotel")
    String description;

    @Schema(description = "Icon identifier for the frontend", example = "wifi")
    String iconName;
}
