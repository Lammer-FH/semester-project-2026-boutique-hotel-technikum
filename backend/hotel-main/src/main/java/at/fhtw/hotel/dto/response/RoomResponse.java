package at.fhtw.hotel.dto.response;

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
    List<ExtraResponse> extras;

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
