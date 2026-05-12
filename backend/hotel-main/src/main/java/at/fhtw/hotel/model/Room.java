package at.fhtw.hotel.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Room {
    private final Long id;
    private final String title;
    private final String description;
    private final int maxGuests;
    private final BigDecimal basePricePerNight;
    private final List<RoomImage> images;
    private final List<Extra> extras;
}
