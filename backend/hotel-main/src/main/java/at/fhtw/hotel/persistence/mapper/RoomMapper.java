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
                .map(this::toDomain)
                .toList();

        return Room.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .maxGuests(entity.getMaxGuests())
                .basePricePerNight(entity.getBasePricePerNight())
                .images(images)
                .extras(extras)
                .build();
    }

    private RoomImage toDomain(RoomImageEntity entity) {
        return RoomImage.builder()
                .id(entity.getId())
                .roomId(entity.getRoom().getId())
                .fileName(entity.getFileName())
                .altText(entity.getAltText())
                .sortOrder(entity.getSortOrder())
                .build();
    }

    private Extra toDomain(ExtraEntity entity) {
        return Extra.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .iconName(entity.getIconName())
                .build();
    }
}
