package at.fhtw.hotel.mapper;

import at.fhtw.hotel.model.Extra;
import at.fhtw.hotel.persistence.entity.ExtraEntity;

public class ExtraMapper {

    public Extra toDomain(ExtraEntity entity) {
        return Extra.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .iconName(entity.getIconName())
                .build();
    }
}
