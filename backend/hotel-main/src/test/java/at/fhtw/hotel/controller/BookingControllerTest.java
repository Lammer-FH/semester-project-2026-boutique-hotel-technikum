package at.fhtw.hotel.controller;

import at.fhtw.hotel.config.ApiRoutes;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.controller.dto.response.BookingResponse;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.service.BookingService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BookingController.class, properties = {"spring.jackson.property-naming-strategy=SNAKE_CASE"})
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void createBooking_withValidRequest_returns201() throws Exception {
        BookingResponse response = BookingResponse.builder()
                .bookingId(42L)
                .checkInDate(LocalDate.of(2026, 6, 1))
                .checkOutDate(LocalDate.of(2026, 6, 6))
                .breakfastIncluded(true)
                .totalPrice(new BigDecimal("905.00"))
                .priceBreakdown(BookingResponse.PriceBreakdown.builder()
                        .nights(5)
                        .roomRate(new BigDecimal("129.00"))
                        .breakfastRate(new BigDecimal("260.00"))
                        .breakfastPerPersonPerDay(new BigDecimal("26.00"))
                        .build())
                .guest(BookingResponse.Guest.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@example.com")
                        .build())
                .room(RoomResponse.builder()
                        .id(1L)
                        .title("Deluxe Room")
                        .description("Bright room")
                        .maxGuests(2)
                        .basePricePerNight(new BigDecimal("129.00"))
                        .images(List.of())
                        .extras(List.of(
                                Extra.builder().id(1L).code("wifi").title("WiFi").iconName("wifi").build()
                        ))
                        .build())
                .hotelContact(BookingResponse.HotelContact.builder()
                        .name("Boutique Hotel")
                        .street("Hochstadtplatz 6")
                        .city("Vienna")
                        .postalCode("1200")
                        .country("Austria")
                        .email("contact@hotel.com")
                        .phone("+43 1 234567")
                        .build())
                .directions(BookingResponse.Directions.builder()
                        .byTrain("S-Bahn")
                        .byCar("A2")
                        .parking("Garage")
                        .build())
                .build();

        when(bookingService.createBooking(any())).thenReturn(response);

        String requestJson = """
                {
                    "room_id": 1,
                    "guest_first_name": "John",
                    "guest_last_name": "Doe",
                    "guest_email": "john@example.com",
                    "confirm_email": "john@example.com",
                    "guest_count": 2,
                    "check_in_date": "2026-06-01",
                    "check_out_date": "2026-06-06",
                    "breakfast_included": true
                }
                """;

        mockMvc.perform(post(ApiRoutes.API + "/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.booking_id").value(42))
                .andExpect(jsonPath("$.guest.first_name").value("John"))
                .andExpect(jsonPath("$.guest.last_name").value("Doe"))
                .andExpect(jsonPath("$.room.title").value("Deluxe Room"))
                .andExpect(jsonPath("$.price_breakdown.nights").value(5))
                .andExpect(jsonPath("$.hotel_contact.name").value("Boutique Hotel"))
                .andExpect(jsonPath("$.directions.by_train").value("S-Bahn"));
    }

    @Test
    void createBooking_withInvalidBody_returns400() throws Exception {
        String invalidJson = """
                {
                    "room_id": null,
                    "guest_first_name": ""
                }
                """;

        mockMvc.perform(post(ApiRoutes.API + "/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBooking_withEmailMismatch_returns400() throws Exception {
        String requestJson = """
                {
                    "room_id": 1,
                    "guest_first_name": "John",
                    "guest_last_name": "Doe",
                    "guest_email": "john@example.com",
                    "confirm_email": "different@example.com",
                    "guest_count": 2,
                    "check_in_date": "2026-06-01",
                    "check_out_date": "2026-06-06",
                    "breakfast_included": true
                }
                """;

        mockMvc.perform(post(ApiRoutes.API + "/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.error.details[0].message").value("Email confirmation does not match"));
    }

    @Test
    void getBooking_withValidId_returnsBooking() throws Exception {
        BookingResponse response = BookingResponse.builder()
                .bookingId(42L)
                .checkInDate(LocalDate.of(2026, 6, 1))
                .checkOutDate(LocalDate.of(2026, 6, 6))
                .breakfastIncluded(true)
                .totalPrice(new BigDecimal("905.00"))
                .priceBreakdown(BookingResponse.PriceBreakdown.builder()
                        .nights(5).roomRate(new BigDecimal("129.00"))
                        .breakfastRate(new BigDecimal("260.00"))
                        .breakfastPerPersonPerDay(new BigDecimal("26.00"))
                        .build())
                .guest(BookingResponse.Guest.builder()
                        .firstName("John").lastName("Doe").email("john@example.com").build())
                .room(RoomResponse.builder().id(1L).title("Deluxe Room").maxGuests(2)
                        .basePricePerNight(new BigDecimal("129.00"))
                        .images(List.of()).extras(List.of()).build())
                .hotelContact(BookingResponse.HotelContact.builder()
                        .name("Boutique Hotel").street("Hochstadtplatz 6")
                        .city("Vienna").postalCode("1200").country("Austria")
                        .email("contact@hotel.com").phone("+43 1 234567").build())
                .directions(BookingResponse.Directions.builder()
                        .byTrain("S-Bahn").byCar("A2").parking("Garage").build())
                .build();

        when(bookingService.getBooking(42L)).thenReturn(response);

        mockMvc.perform(get(ApiRoutes.API + "/bookings/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.booking_id").value(42))
                .andExpect(jsonPath("$.guest.first_name").value("John"));
    }

    @Test
    void getBooking_withInvalidId_returns404() throws Exception {
        when(bookingService.getBooking(99L))
                .thenThrow(new DomainException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found"));

        mockMvc.perform(get(ApiRoutes.API + "/bookings/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("BOOKING_NOT_FOUND"));
    }

    @Test
    void createBooking_withUnavailableDates_returns409() throws Exception {
        when(bookingService.createBooking(any()))
                .thenThrow(new DomainException(ErrorCode.DATES_UNAVAILABLE, "Requested dates are unavailable"));

        String requestJson = """
                {
                    "room_id": 1,
                    "guest_first_name": "John",
                    "guest_last_name": "Doe",
                    "guest_email": "john@example.com",
                    "confirm_email": "john@example.com",
                    "guest_count": 2,
                    "check_in_date": "2026-06-01",
                    "check_out_date": "2026-06-06",
                    "breakfast_included": true
                }
                """;

        mockMvc.perform(post(ApiRoutes.API + "/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.code").value("DATES_UNAVAILABLE"));
    }
}
