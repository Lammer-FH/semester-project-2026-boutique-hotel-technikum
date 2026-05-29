package at.fhtw.hotel.application.dto;

import java.time.LocalDate;

public record CreateBookingCommand(
        long roomId,
        String guestFirstName,
        String guestLastName,
        String guestEmail,
        int guestCount,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        boolean breakfastIncluded
) {}
