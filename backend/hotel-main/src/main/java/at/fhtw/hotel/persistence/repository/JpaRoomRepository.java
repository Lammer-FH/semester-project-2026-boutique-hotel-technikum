package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.persistence.entity.RoomEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomRepository extends JpaRepository<RoomEntity, Long> {

    @EntityGraph(attributePaths = {"images", "roomExtras", "roomExtras.extra"})
    java.util.List<RoomEntity> findAllByOrderByIdAsc(org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = {"images", "roomExtras", "roomExtras.extra"})
    java.util.Optional<RoomEntity> findById(Long id);
}
