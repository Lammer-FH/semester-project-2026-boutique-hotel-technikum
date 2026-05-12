package at.fhtw.hotel.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Booking {
    private final Long id;
    private final Long roomId;
    private final String guestFirstName;
    private final String guestLastName;
    private final String guestEmail;
    private final int guestCount;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final boolean breakfastIncluded;
    private final BigDecimal totalPrice;
}
