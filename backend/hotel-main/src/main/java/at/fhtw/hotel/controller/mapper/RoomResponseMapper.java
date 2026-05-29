package at.fhtw.hotel.controller.mapper;

import at.fhtw.hotel.controller.dto.response.ExtraResponse;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.domain.model.RoomImage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RoomResponseMapper {

    public RoomResponse toResponse(Room room) {
        List<RoomResponse.RoomImageResponse> images = room.images().stream()
                .map(this::toImage)
                .toList();

        List<ExtraResponse> extras = room.extras().stream()
                .map(this::toExtra)
                .toList();

        return RoomResponse.builder()
                .id(room.id())
                .title(room.title())
                .description(room.description())
                .maxGuests(room.maxGuests())
                .basePricePerNight(room.basePricePerNight())
                .images(images)
                .extras(extras)
                .build();
    }

    private RoomResponse.RoomImageResponse toImage(RoomImage image) {
        return RoomResponse.RoomImageResponse.builder()
                .id(image.id())
                .url("/images/rooms/" + image.roomId() + "/" + image.fileName())
                .sortOrder(image.sortOrder())
                .altText(image.altText())
                .build();
    }

    private ExtraResponse toExtra(Extra extra) {
        return ExtraResponse.builder()
                .id(extra.id())
                .code(extra.code())
                .title(extra.title())
                .description(extra.description())
                .iconName(extra.iconName())
                .build();
    }
}
