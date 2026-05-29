package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.persistence.repository.JpaBookingRepository;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvailabilityService {

    private static final Logger log = LoggerFactory.getLogger(AvailabilityService.class);

    private final JpaRoomRepository jpaRoomRepository;
    private final JpaBookingRepository jpaBookingRepository;

    public AvailabilityService(JpaRoomRepository jpaRoomRepository, JpaBookingRepository jpaBookingRepository) {
        this.jpaRoomRepository = jpaRoomRepository;
        this.jpaBookingRepository = jpaBookingRepository;
    }

    @Transactional(readOnly = true)
    public boolean isRoomAvailable(long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        log.debug("Checking availability roomId={} checkIn={} checkOut={}", roomId, checkInDate, checkOutDate);
        if (!jpaRoomRepository.existsById(roomId)) {
            throw new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found");
        }
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new DomainException(ErrorCode.INVALID_DATE_RANGE, "Check-out must be after check-in");
        }
        return !jpaBookingRepository.existsOverlappingBooking(roomId, checkInDate, checkOutDate);
    }
}
