package at.fhtw.hotel.repository;

import at.fhtw.hotel.model.Booking;
import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(long bookingId);
    boolean existsOverlappingBooking(long roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
