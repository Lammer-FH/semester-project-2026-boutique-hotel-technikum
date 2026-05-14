package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.entity.RoomEntity;
import at.fhtw.hotel.persistence.mapper.RoomMapper;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private JpaRoomRepository jpaRoomRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomService roomService;

    @Test
    void listRooms_returnsPaginatedRooms() {
        RoomEntity entity1 = new RoomEntity();
        RoomEntity entity2 = new RoomEntity();
        Room room1 = createRoom(1L);
        Room room2 = createRoom(2L);

        when(jpaRoomRepository.findAllByOrderByIdAsc(PageRequest.of(0, 5))).thenReturn(List.of(entity1, entity2));
        when(roomMapper.toDomain(entity1)).thenReturn(room1);
        when(roomMapper.toDomain(entity2)).thenReturn(room2);

        assertThat(roomService.listRooms(0, 5)).containsExactly(room1, room2);
    }

    @Test
    void countRooms_returnsTotalCount() {
        when(jpaRoomRepository.count()).thenReturn(10L);
        assertThat(roomService.countRooms()).isEqualTo(10L);
    }

    @Test
    void getRoom_withValidId_returnsRoom() {
        RoomEntity entity = new RoomEntity();
        Room expected = createRoom(1L);

        when(jpaRoomRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(roomMapper.toDomain(entity)).thenReturn(expected);

        assertThat(roomService.getRoom(1L)).isEqualTo(expected);
    }

    @Test
    void getRoom_withInvalidId_throwsDomainException() {
        when(jpaRoomRepository.findById(99L)).thenReturn(Optional.empty());
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
