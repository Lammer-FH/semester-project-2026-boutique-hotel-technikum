package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.mapper.RoomMapper;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.repository.RoomRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryAdapter implements RoomRepository {

    private final JpaRoomRepository jpaRoomRepository;
    private final RoomMapper roomMapper;

    public RoomRepositoryAdapter(JpaRoomRepository jpaRoomRepository, RoomMapper roomMapper) {
        this.jpaRoomRepository = jpaRoomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public List<Room> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return jpaRoomRepository.findAllByOrderByIdAsc(pageable).stream()
                .map(roomMapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return jpaRoomRepository.count();
    }

    @Override
    public Optional<Room> findById(long roomId) {
        return jpaRoomRepository.findById(roomId)
                .map(roomMapper::toDomain);
    }
}
