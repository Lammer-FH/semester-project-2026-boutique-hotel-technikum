package at.fhtw.hotel.mapper;

import at.fhtw.hotel.model.Booking;
import at.fhtw.hotel.persistence.entity.BookingEntity;

public class BookingMapper {

    public Booking toDomain(BookingEntity entity) {
        return Booking.builder()
                .id(entity.getId())
                .roomId(entity.getRoom().getId())
                .guestFirstName(entity.getGuestFirstName())
                .guestLastName(entity.getGuestLastName())
                .guestEmail(entity.getGuestEmail())
                .guestCount(entity.getGuestCount())
                .checkInDate(entity.getCheckInDate())
                .checkOutDate(entity.getCheckOutDate())
                .breakfastIncluded(entity.isBreakfastIncluded())
                .totalPrice(entity.getTotalPrice())
                .build();
    }
}
