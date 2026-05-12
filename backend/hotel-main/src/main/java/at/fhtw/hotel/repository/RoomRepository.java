package at.fhtw.hotel.repository;

import at.fhtw.hotel.model.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findAll(int page, int size);
    long count();
    Optional<Room> findById(long roomId);
}
