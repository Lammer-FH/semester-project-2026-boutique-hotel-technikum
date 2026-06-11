package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.persistence.entity.RoomEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaRoomRepository extends JpaRepository<RoomEntity, Long> {

    java.util.List<RoomEntity> findAllByOrderByIdAsc(org.springframework.data.domain.Pageable pageable);

    @EntityGraph(attributePaths = {"images", "extras"})
    java.util.List<RoomEntity> findAllByIdInOrderByIdAsc(java.util.List<Long> ids);

    @EntityGraph(attributePaths = {"images", "extras"})
    java.util.Optional<RoomEntity> findById(Long id);

    @Query("select r.id from RoomEntity r where not exists "
            + "(select b.id from BookingEntity b where b.room.id = r.id "
            + "and b.checkInDate < :checkOutDate and b.checkOutDate > :checkInDate) "
            + "order by r.id asc")
    java.util.List<Long> findAvailableRoomIds(@Param("checkInDate") LocalDate checkInDate,
                                              @Param("checkOutDate") LocalDate checkOutDate,
                                              org.springframework.data.domain.Pageable pageable);

    @Query("select count(r) from RoomEntity r where not exists "
            + "(select b.id from BookingEntity b where b.room.id = r.id "
            + "and b.checkInDate < :checkOutDate and b.checkOutDate > :checkInDate)")
    long countAvailableRooms(@Param("checkInDate") LocalDate checkInDate,
                             @Param("checkOutDate") LocalDate checkOutDate);
}
