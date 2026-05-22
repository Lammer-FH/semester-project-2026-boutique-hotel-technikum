package at.fhtw.hotel.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Booking {
    Long id;
    Long roomId;
    String guestFirstName;
    String guestLastName;
    String guestEmail;
    int guestCount;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    boolean breakfastIncluded;
    BigDecimal totalPrice;
}
