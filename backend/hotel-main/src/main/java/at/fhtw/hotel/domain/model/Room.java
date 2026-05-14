package at.fhtw.hotel.domain.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Room {
    Long id;
    String title;
    String description;
    int maxGuests;
    BigDecimal basePricePerNight;
    List<RoomImage> images;
    List<Extra> extras;
}
