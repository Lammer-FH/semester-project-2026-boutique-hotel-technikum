package at.fhtw.hotel.persistence.mapper;

import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.persistence.entity.ExtraEntity;
import org.springframework.stereotype.Component;

@Component
public class ExtraMapper {

    public Extra toDomain(ExtraEntity entity) {
        return new Extra(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIconName()
        );
    }
}
