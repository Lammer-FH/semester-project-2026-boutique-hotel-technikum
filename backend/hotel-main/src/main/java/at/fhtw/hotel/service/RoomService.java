package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.mapper.RoomMapper;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final JpaRoomRepository jpaRoomRepository;
    private final RoomMapper roomMapper;

    public RoomService(JpaRoomRepository jpaRoomRepository, RoomMapper roomMapper) {
        this.jpaRoomRepository = jpaRoomRepository;
        this.roomMapper = roomMapper;
    }

    public List<Room> listRooms(int page, int size) {
        log.debug("Listing rooms page={} size={}", page, size);
        return jpaRoomRepository.findAllByOrderByIdAsc(PageRequest.of(page, size)).stream()
                .map(roomMapper::toDomain)
                .toList();
    }

    public long countRooms() {
        return jpaRoomRepository.count();
    }

    public Room getRoom(long roomId) {
        return jpaRoomRepository.findById(roomId)
                .map(roomMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));
    }
}
