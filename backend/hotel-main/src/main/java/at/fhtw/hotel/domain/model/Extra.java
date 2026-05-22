package at.fhtw.hotel.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Extra {
    Long id;
    String code;
    String title;
    String description;
    String iconName;
}
