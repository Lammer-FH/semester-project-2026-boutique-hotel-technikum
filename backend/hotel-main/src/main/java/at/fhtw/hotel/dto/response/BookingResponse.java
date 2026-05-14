package at.fhtw.hotel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Complete booking confirmation with pricing breakdown")
public class BookingResponse {
    @Schema(description = "Unique booking identifier", example = "42")
    Long bookingId;

    @Schema(description = "Check-in date", example = "2026-06-15")
    LocalDate checkInDate;

    @Schema(description = "Check-out date", example = "2026-06-18")
    LocalDate checkOutDate;

    @Schema(description = "Whether breakfast is included", example = "true")
    boolean breakfastIncluded;

    @Schema(description = "Total booking price in EUR", example = "675.00")
    BigDecimal totalPrice;

    @Schema(description = "Detailed price breakdown")
    PriceBreakdown priceBreakdown;

    @Schema(description = "Guest information")
    Guest guest;

    @Schema(description = "Booked room details")
    RoomResponse room;

    @Schema(description = "Hotel contact information")
    HotelContact hotelContact;

    @Schema(description = "Directions to the hotel")
    Directions directions;

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "Itemized price breakdown for the booking")
    public static class PriceBreakdown {
        @Schema(description = "Number of nights", example = "3")
        int nights;

        @Schema(description = "Room rate subtotal in EUR", example = "597.00")
        BigDecimal roomRate;

        @Schema(description = "Breakfast subtotal in EUR", example = "78.00")
        BigDecimal breakfastRate;

        @Schema(description = "Breakfast price per person per day in EUR", example = "26.00")
        BigDecimal breakfastPerPersonPerDay;
    }

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "Guest personal information")
    public static class Guest {
        @Schema(description = "Guest first name", example = "John")
        String firstName;

        @Schema(description = "Guest last name", example = "Doe")
        String lastName;

        @Schema(description = "Guest email address", example = "john.doe@example.com")
        String email;
    }

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "Hotel contact details")
    public static class HotelContact {
        @Schema(description = "Hotel name", example = "Boutique Hotel Technikum")
        String name;

        @Schema(description = "Street address", example = "Höchstädtplatz 6")
        String street;

        @Schema(description = "City", example = "Vienna")
        String city;

        @Schema(description = "Postal code", example = "1200")
        String postalCode;

        @Schema(description = "Country", example = "Austria")
        String country;

        @Schema(description = "Email address", example = "contact@technikum-wien.example")
        String email;

        @Schema(description = "Phone number", example = "+43 1 234567")
        String phone;
    }

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "Directions to the hotel")
    public static class Directions {
        @Schema(description = "Directions by train", example = "S-Bahn S1 or S2 to Technikum Wien (5 min walk)")
        String byTrain;

        @Schema(description = "Directions by car", example = "A2 exit 'Inzersdorf', follow B151 towards city center")
        String byCar;

        @Schema(description = "Parking information", example = "Public parking garage (200m, €12/day)")
        String parking;
    }
}
