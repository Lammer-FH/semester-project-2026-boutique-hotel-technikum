package at.fhtw.hotel.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomImage {
    Long id;
    Long roomId;
    String fileName;
    String altText;
    int sortOrder;

    public String imageUrl() {
        return "/images/rooms/" + roomId + "/" + fileName;
    }
}
