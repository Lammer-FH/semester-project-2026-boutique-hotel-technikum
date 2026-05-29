package at.fhtw.hotel.domain.model;

public record Extra(
        Long id,
        String code,
        String title,
        String description,
        String iconName
) {}
