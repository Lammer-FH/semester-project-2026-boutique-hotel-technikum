package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.entity.RoomEntity;
import at.fhtw.hotel.persistence.mapper.RoomMapper;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import java.time.LocalDate;
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
        return listRooms(page, size, null, null);
    }

    public List<Room> listRooms(int page, int size, LocalDate checkInDate, LocalDate checkOutDate) {
        log.debug("Listing rooms page={} size={} checkIn={} checkOut={}", page, size, checkInDate, checkOutDate);
        List<Long> roomIds = resolveRoomIds(page, size, checkInDate, checkOutDate);

        if (roomIds.isEmpty()) {
            return List.of();
        }

        return jpaRoomRepository.findAllByIdInOrderByIdAsc(roomIds).stream()
                .map(roomMapper::toDomain)
                .toList();
    }

    public long countRooms() {
        return countRooms(null, null);
    }

    public long countRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        if (hasDateFilter(checkInDate, checkOutDate)) {
            validateDateRange(checkInDate, checkOutDate);
            return jpaRoomRepository.countAvailableRooms(checkInDate, checkOutDate);
        }
        return jpaRoomRepository.count();
    }

    private List<Long> resolveRoomIds(int page, int size, LocalDate checkInDate, LocalDate checkOutDate) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (hasDateFilter(checkInDate, checkOutDate)) {
            validateDateRange(checkInDate, checkOutDate);
            return jpaRoomRepository.findAvailableRoomIds(checkInDate, checkOutDate, pageRequest);
        }
        return jpaRoomRepository.findAllByOrderByIdAsc(pageRequest).stream()
                .map(RoomEntity::getId)
                .toList();
    }

    private boolean hasDateFilter(LocalDate checkInDate, LocalDate checkOutDate) {
        return checkInDate != null && checkOutDate != null;
    }

    private void validateDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new DomainException(ErrorCode.INVALID_DATE_RANGE, "Check-out must be after check-in");
        }
    }

    public Room getRoom(long roomId) {
        return jpaRoomRepository.findById(roomId)
                .map(roomMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));
    }
}
