package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.repository.RoomRepository;
import java.math.BigDecimal;
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
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    void listRooms_returnsPaginatedRooms() {
        List<Room> expected = List.of(createRoom(1L), createRoom(2L));
        when(roomRepository.findAll(0, 5)).thenReturn(expected);
        assertThat(roomService.listRooms(0, 5)).isEqualTo(expected);
    }

    @Test
    void countRooms_returnsTotalCount() {
        when(roomRepository.count()).thenReturn(10L);
        assertThat(roomService.countRooms()).isEqualTo(10L);
    }

    @Test
    void getRoom_withValidId_returnsRoom() {
        Room expected = createRoom(1L);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(expected));
        assertThat(roomService.getRoom(1L)).isEqualTo(expected);
    }

    @Test
    void getRoom_withInvalidId_throwsDomainException() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> roomService.getRoom(99L))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.ROOM_NOT_FOUND);
    }

    private Room createRoom(Long id) {
        return Room.builder()
                .id(id)
                .title("Room " + id)
                .description("Description " + id)
                .maxGuests(2)
                .basePricePerNight(new BigDecimal("100.00"))
                .images(List.of())
                .extras(List.of())
                .build();
    }
}
