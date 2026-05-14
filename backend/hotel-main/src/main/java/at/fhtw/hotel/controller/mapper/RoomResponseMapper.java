package at.fhtw.hotel.controller.mapper;

import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.domain.model.RoomImage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RoomResponseMapper {

    public RoomResponse toResponse(Room room) {
        List<RoomResponse.RoomImageResponse> images = room.getImages().stream()
                .map(this::toImage)
                .toList();

        List<Extra> extras = room.getExtras();

        return RoomResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .description(room.getDescription())
                .maxGuests(room.getMaxGuests())
                .basePricePerNight(room.getBasePricePerNight())
                .images(images)
                .extras(extras)
                .build();
    }

    private RoomResponse.RoomImageResponse toImage(RoomImage image) {
        return RoomResponse.RoomImageResponse.builder()
                .id(image.getId())
                .url(image.imageUrl())
                .sortOrder(image.getSortOrder())
                .altText(image.getAltText())
                .build();
    }
}
