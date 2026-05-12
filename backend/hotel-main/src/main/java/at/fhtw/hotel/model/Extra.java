package at.fhtw.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Extra {
    private final Long id;
    private final String code;
    private final String title;
    private final String description;
    private final String iconName;
}
