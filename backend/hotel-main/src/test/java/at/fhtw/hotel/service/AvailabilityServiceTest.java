package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.repository.BookingRepository;
import at.fhtw.hotel.repository.RoomRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    private final Room room = Room.builder()
            .id(1L)
            .title("Test Room")
            .maxGuests(2)
            .basePricePerNight(new BigDecimal("100.00"))
            .images(List.of())
            .extras(List.of())
            .build();

    private final LocalDate checkIn = LocalDate.of(2026, 6, 1);
    private final LocalDate checkOut = LocalDate.of(2026, 6, 5);

    @Test
    void isRoomAvailable_noOverlap_returnsTrue() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(false);
        assertThat(availabilityService.isRoomAvailable(1L, checkIn, checkOut)).isTrue();
    }

    @Test
    void isRoomAvailable_overlapExists_returnsFalse() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(true);
        assertThat(availabilityService.isRoomAvailable(1L, checkIn, checkOut)).isFalse();
    }

    @Test
    void isRoomAvailable_invalidRoomId_throwsDomainException() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> availabilityService.isRoomAvailable(99L, checkIn, checkOut))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.ROOM_NOT_FOUND);
    }

    @Test
    void isRoomAvailable_invalidDateRange_throwsDomainException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        LocalDate badCheckIn = LocalDate.of(2026, 6, 10);
        LocalDate badCheckOut = LocalDate.of(2026, 6, 5);
        assertThatThrownBy(() -> availabilityService.isRoomAvailable(1L, badCheckIn, badCheckOut))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.INVALID_DATE_RANGE);
    }
}
