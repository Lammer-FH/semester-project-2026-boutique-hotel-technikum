package at.fhtw.hotel.persistence.mapper;

import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.persistence.entity.ExtraEntity;
import org.springframework.stereotype.Component;

@Component
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
