package at.fhtw.hotel.persistence.mapper;

import at.fhtw.hotel.domain.model.Booking;
import at.fhtw.hotel.persistence.entity.BookingEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toDomain(BookingEntity entity) {
        return new Booking(
                entity.getId(),
                entity.getRoom().getId(),
                entity.getGuestFirstName(),
                entity.getGuestLastName(),
                entity.getGuestEmail(),
                entity.getGuestCount(),
                entity.getCheckInDate(),
                entity.getCheckOutDate(),
                entity.isBreakfastIncluded(),
                entity.getTotalPrice(),
                entity.getNights(),
                entity.getRoomRatePerNight(),
                entity.getBreakfastRate(),
                entity.getBreakfastPerPersonPerDay()
        );
    }
}
