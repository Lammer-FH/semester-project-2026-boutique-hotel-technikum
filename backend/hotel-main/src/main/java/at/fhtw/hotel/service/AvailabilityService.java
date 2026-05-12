package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.repository.BookingRepository;
import at.fhtw.hotel.repository.RoomRepository;
import at.fhtw.hotel.util.Logger;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {

    private static final Logger log = Logger.get(AvailabilityService.class);

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public AvailabilityService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public boolean isRoomAvailable(long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        log.debug("Checking availability roomId={} checkIn={} checkOut={}", roomId, checkInDate, checkOutDate);
        roomRepository.findById(roomId)
                .orElseThrow(() -> new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new DomainException(ErrorCode.INVALID_DATE_RANGE, "Check-out must be after check-in");
        }
        return !bookingRepository.existsOverlappingBooking(roomId, checkInDate, checkOutDate);
    }
}
