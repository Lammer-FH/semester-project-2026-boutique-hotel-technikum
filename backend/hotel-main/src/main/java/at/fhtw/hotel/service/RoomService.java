package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.repository.RoomRepository;
import at.fhtw.hotel.util.Logger;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private static final Logger log = Logger.get(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> listRooms(int page, int size) {
        log.debug("Listing rooms page={} size={}", page, size);
        return roomRepository.findAll(page, size);
    }

    public long countRooms() {
        return roomRepository.count();
    }

    public Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));
    }
}
