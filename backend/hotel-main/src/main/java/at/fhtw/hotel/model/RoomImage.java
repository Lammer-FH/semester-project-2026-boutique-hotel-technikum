package at.fhtw.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoomImage {
    private final Long id;
    private final Long roomId;
    private final String fileName;
    private final String altText;
    private final int sortOrder;
}
