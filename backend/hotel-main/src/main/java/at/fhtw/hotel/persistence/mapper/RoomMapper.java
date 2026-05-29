package at.fhtw.hotel.persistence.mapper;

import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.domain.model.RoomImage;
import at.fhtw.hotel.persistence.entity.ExtraEntity;
import at.fhtw.hotel.persistence.entity.RoomEntity;
import at.fhtw.hotel.persistence.entity.RoomImageEntity;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room toDomain(RoomEntity entity) {
        List<RoomImage> images = entity.getImages().stream()
                .sorted(Comparator.comparingInt(RoomImageEntity::getSortOrder))
                .map(this::toDomain)
                .toList();

        List<Extra> extras = entity.getExtras().stream()
                .sorted(Comparator.comparing(ExtraEntity::getId, Comparator.nullsLast(Long::compareTo)))
                .map(this::toDomain)
                .toList();

        return new Room(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMaxGuests(),
                entity.getBasePricePerNight(),
                images,
                extras
        );
    }

    private RoomImage toDomain(RoomImageEntity entity) {
        return new RoomImage(
                entity.getId(),
                entity.getRoom().getId(),
                entity.getFileName(),
                entity.getAltText(),
                entity.getSortOrder()
        );
    }

    private Extra toDomain(ExtraEntity entity) {
        return new Extra(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIconName()
        );
    }
}
