package at.fhtw.hotel.service;

import at.fhtw.hotel.dto.response.ExtraResponse;
import at.fhtw.hotel.dto.response.RoomResponse;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.model.RoomImage;
import java.util.List;

public class RoomResponseMapper {

    public static RoomResponse toResponse(Room room) {
        List<RoomResponse.RoomImageResponse> images = room.getImages().stream()
                .map(RoomResponseMapper::toImage)
                .toList();

        List<ExtraResponse> extras = room.getExtras().stream()
                .map(extra -> ExtraResponse.builder()
                        .id(extra.getId())
                        .code(extra.getCode())
                        .title(extra.getTitle())
                        .description(extra.getDescription())
                        .iconName(extra.getIconName())
                        .build())
                .toList();

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

    private static RoomResponse.RoomImageResponse toImage(RoomImage image) {
        String url = "/images/rooms/" + image.getRoomId() + "/" + image.getFileName();
        return RoomResponse.RoomImageResponse.builder()
                .id(image.getId())
                .url(url)
                .sortOrder(image.getSortOrder())
                .altText(image.getAltText())
                .build();
    }
}
