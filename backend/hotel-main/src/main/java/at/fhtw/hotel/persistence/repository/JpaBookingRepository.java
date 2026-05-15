package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.persistence.entity.BookingEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("select count(b) > 0 from BookingEntity b where b.room.id = :roomId and b.checkInDate < :checkOutDate and b.checkOutDate > :checkInDate")
    boolean existsOverlappingBooking(@Param("roomId") long roomId,
                                     @Param("checkInDate") LocalDate checkInDate,
                                     @Param("checkOutDate") LocalDate checkOutDate);
}
