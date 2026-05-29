package at.fhtw.hotel.controller.mapper;

import at.fhtw.hotel.controller.dto.response.ExtraResponse;
import at.fhtw.hotel.domain.model.Extra;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExtraResponseMapper {

    public ExtraResponse toResponse(Extra extra) {
        return ExtraResponse.builder()
                .id(extra.id())
                .code(extra.code())
                .title(extra.title())
                .description(extra.description())
                .iconName(extra.iconName())
                .build();
    }

    public List<ExtraResponse> toResponseList(List<Extra> extras) {
        return extras.stream().map(this::toResponse).toList();
    }
}
