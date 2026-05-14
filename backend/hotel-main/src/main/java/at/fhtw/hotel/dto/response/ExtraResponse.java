package at.fhtw.hotel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "A bookable extra or amenity offered by the hotel")
public class ExtraResponse {
    @Schema(description = "Extra identifier", example = "1")
    Long id;

    @Schema(description = "Unique code for the extra", example = "BREAKFAST")
    String code;

    @Schema(description = "Display title", example = "Breakfast Buffet")
    String title;

    @Schema(description = "Description of the extra", example = "Enjoy our daily breakfast buffet with local delicacies")
    String description;

    @Schema(description = "Icon name for UI rendering", example = "coffee")
    String iconName;
}
