package at.fhtw.hotel.domain.model;

public record RoomImage(
        Long id,
        Long roomId,
        String fileName,
        String altText,
        int sortOrder
) {}
