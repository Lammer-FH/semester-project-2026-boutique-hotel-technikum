package at.fhtw.hotel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BookingResponse {
    Long bookingId;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    boolean breakfastIncluded;
    BigDecimal totalPrice;
    PriceBreakdown priceBreakdown;
    Guest guest;
    RoomResponse room;
    HotelContact hotelContact;
    Directions directions;

    @Value
    @Builder
    @Jacksonized
    public static class PriceBreakdown {
        int nights;
        BigDecimal roomRate;
        BigDecimal breakfastRate;
        BigDecimal breakfastPerPersonPerDay;
    }

    @Value
    @Builder
    @Jacksonized
    public static class Guest {
        String firstName;
        String lastName;
        String email;
    }

    @Value
    @Builder
    @Jacksonized
    public static class HotelContact {
        String name;
        String street;
        String city;
        String postalCode;
        String country;
        String email;
        String phone;
    }

    @Value
    @Builder
    @Jacksonized
    public static class Directions {
        String byTrain;
        String byCar;
        String parking;
    }
}
