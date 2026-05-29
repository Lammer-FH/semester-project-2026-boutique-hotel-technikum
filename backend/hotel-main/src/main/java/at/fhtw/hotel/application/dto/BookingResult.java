package at.fhtw.hotel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResult(
        long bookingId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        boolean breakfastIncluded,
        int guestCount,
        BigDecimal totalPrice,
        int nights,
        BigDecimal roomRatePerNight,
        BigDecimal breakfastRate,
        BigDecimal breakfastPerPersonPerDay,
        String guestFirstName,
        String guestLastName,
        String guestEmail,
        long roomId
) {}
