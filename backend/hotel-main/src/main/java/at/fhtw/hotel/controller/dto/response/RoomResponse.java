package at.fhtw.hotel.controller.dto.response;

import at.fhtw.hotel.domain.model.Extra;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class RoomResponse {
    Long id;
    String title;
    String description;
    int maxGuests;
    BigDecimal basePricePerNight;
    List<RoomImageResponse> images;
    List<Extra> extras;

    @Value
    @Builder
    @Jacksonized
    public static class RoomImageResponse {
        Long id;
        String url;
        int sortOrder;
        String altText;
    }
}
