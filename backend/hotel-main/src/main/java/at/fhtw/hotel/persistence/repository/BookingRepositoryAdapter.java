package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.mapper.BookingMapper;
import at.fhtw.hotel.model.Booking;
import at.fhtw.hotel.persistence.entity.BookingEntity;
import at.fhtw.hotel.persistence.entity.RoomEntity;
import at.fhtw.hotel.repository.BookingRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepositoryAdapter implements BookingRepository {

    private final JpaBookingRepository jpaBookingRepository;
    private final BookingMapper bookingMapper;
    private final EntityManager entityManager;

    public BookingRepositoryAdapter(JpaBookingRepository jpaBookingRepository,
                                    BookingMapper bookingMapper,
                                    EntityManager entityManager) {
        this.jpaBookingRepository = jpaBookingRepository;
        this.bookingMapper = bookingMapper;
        this.entityManager = entityManager;
    }

    @Override
    public Booking save(Booking booking) {
        BookingEntity entity = new BookingEntity();
        RoomEntity room = entityManager.getReference(RoomEntity.class, booking.getRoomId());
        entity.setRoom(room);
        entity.setGuestFirstName(booking.getGuestFirstName());
        entity.setGuestLastName(booking.getGuestLastName());
        entity.setGuestEmail(booking.getGuestEmail());
        entity.setGuestCount(booking.getGuestCount());
        entity.setCheckInDate(booking.getCheckInDate());
        entity.setCheckOutDate(booking.getCheckOutDate());
        entity.setBreakfastIncluded(booking.isBreakfastIncluded());
        entity.setTotalPrice(booking.getTotalPrice());
        BookingEntity saved = jpaBookingRepository.save(entity);
        return bookingMapper.toDomain(saved);
    }

    @Override
    public Optional<Booking> findById(long bookingId) {
        return jpaBookingRepository.findById(bookingId)
                .map(bookingMapper::toDomain);
    }

    @Override
    public boolean existsOverlappingBooking(long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return jpaBookingRepository.existsOverlappingBooking(roomId, checkInDate, checkOutDate);
    }
}
