package at.fhtw.hotel.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record Room(
        Long id,
        String title,
        String description,
        int maxGuests,
        BigDecimal basePricePerNight,
        List<RoomImage> images,
        List<Extra> extras
) {}
