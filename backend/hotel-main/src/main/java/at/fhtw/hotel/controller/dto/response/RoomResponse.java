package at.fhtw.hotel.controller.dto.response;

import at.fhtw.hotel.domain.model.Extra;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Detailed room information including images and bookable extras")
public class RoomResponse {
    @Schema(description = "Unique room identifier", example = "1")
    Long id;

    @Schema(description = "Room title / name", example = "Deluxe Suite")
    String title;

    @Schema(description = "Detailed room description", example = "A spacious suite with city view...")
    String description;

    @Schema(description = "Maximum number of guests", example = "2")
    int maxGuests;

    @Schema(description = "Base price per night in EUR", example = "199.00")
    BigDecimal basePricePerNight;

    @Schema(description = "Room images")
    List<RoomImageResponse> images;
    @Schema(description = "Bookable extras associated with this room")
    List<Extra> extras;

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "An image belonging to a room")
    public static class RoomImageResponse {
        @Schema(description = "Image identifier", example = "1")
        Long id;

        @Schema(description = "Image URL", example = "/images/rooms/deluxe-suite-1.jpg")
        String url;

        @Schema(description = "Display sort order", example = "0")
        int sortOrder;

        @Schema(description = "Alt text for accessibility", example = "Deluxe Suite - Main view")
        String altText;
    }
}
