package at.fhtw.hotel.service;

import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.dto.request.BookingRequest;
import at.fhtw.hotel.dto.response.BookingResponse;
import at.fhtw.hotel.model.Booking;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.repository.BookingRepository;
import at.fhtw.hotel.repository.RoomRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomService roomService;

    private HotelProperties hotelProperties;
    private BookingService bookingService;

    private final Room room = Room.builder()
            .id(1L)
            .title("Deluxe Room")
            .description("Bright room with city view")
            .maxGuests(2)
            .basePricePerNight(new BigDecimal("129.00"))
            .images(List.of())
            .extras(List.of())
            .build();

    private final LocalDate checkIn = LocalDate.of(2026, 6, 1);
    private final LocalDate checkOut = LocalDate.of(2026, 6, 6);

    @BeforeEach
    void setUp() {
        hotelProperties = new HotelProperties();
        hotelProperties.setName("Boutique Hotel Technikum");
        hotelProperties.setEmail("contact@hotel.com");
        hotelProperties.setPhone("+43 1 234567");
        hotelProperties.setBreakfastPricePerPerson(26.00);

        HotelProperties.Address address = new HotelProperties.Address();
        address.setStreet("Hochstadtplatz 6");
        address.setCity("Vienna");
        address.setPostalCode("1200");
        address.setCountry("Austria");
        hotelProperties.setAddress(address);

        HotelProperties.Directions directions = new HotelProperties.Directions();
        directions.setByTrain("S-Bahn to Technikum Wien");
        directions.setByCar("A2 exit Inzersdorf");
        directions.setParking("Public parking garage");
        hotelProperties.setDirections(directions);

        bookingService = new BookingService(bookingRepository, roomRepository, hotelProperties, roomService);
    }

    @Test
    void createBooking_success_returnsBookingResponse() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("john@example.com")
                .guestCount(2)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .breakfastIncluded(true)
                .build();

        when(roomService.getRoom(1L)).thenReturn(room);
        when(bookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(false);

        Booking savedBooking = Booking.builder()
                .id(42L)
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .guestCount(2)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .breakfastIncluded(true)
                .totalPrice(new BigDecimal("905.00"))
                .build();

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        when(bookingRepository.save(bookingCaptor.capture())).thenReturn(savedBooking);

        BookingResponse response = bookingService.createBooking(request);

        assertThat(response.getBookingId()).isEqualTo(42L);
        assertThat(response.getGuest().getFirstName()).isEqualTo("John");
        assertThat(response.getGuest().getLastName()).isEqualTo("Doe");
        assertThat(response.getGuest().getEmail()).isEqualTo("john@example.com");
        assertThat(response.getCheckInDate()).isEqualTo(checkIn);
        assertThat(response.getCheckOutDate()).isEqualTo(checkOut);
        assertThat(response.isBreakfastIncluded()).isTrue();
        assertThat(response.getRoom().getId()).isEqualTo(1L);
        assertThat(response.getRoom().getTitle()).isEqualTo("Deluxe Room");
        assertThat(response.getPriceBreakdown().getNights()).isEqualTo(5);
        assertThat(response.getPriceBreakdown().getRoomRate()).isEqualByComparingTo("129.00");
        assertThat(response.getHotelContact().getName()).isEqualTo("Boutique Hotel Technikum");
        assertThat(response.getDirections().getByTrain()).isEqualTo("S-Bahn to Technikum Wien");

        Booking captured = bookingCaptor.getValue();
        assertThat(captured.getRoomId()).isEqualTo(1L);
        assertThat(captured.getGuestCount()).isEqualTo(2);
    }

    @Test
    void createBooking_emailMismatch_throwsDomainException() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("different@example.com")
                .guestCount(2)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .breakfastIncluded(false)
                .build();

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.EMAIL_MISMATCH);
    }

    @Test
    void createBooking_guestCountExceedsMax_throwsDomainException() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("john@example.com")
                .guestCount(5)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .breakfastIncluded(false)
                .build();

        when(roomService.getRoom(1L)).thenReturn(room);

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.INVALID_INPUT);
    }

    @Test
    void createBooking_datesUnavailable_throwsDomainException() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("john@example.com")
                .guestCount(2)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .breakfastIncluded(false)
                .build();

        when(roomService.getRoom(1L)).thenReturn(room);
        when(bookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(true);

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.DATES_UNAVAILABLE);
    }

    @Test
    void getBooking_returnsBookingResponse() {
        LocalDate today = LocalDate.now();
        LocalDate futureCheckIn = today.plusDays(10);
        LocalDate futureCheckOut = today.plusDays(15);

        Booking booking = Booking.builder()
                .id(42L)
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .guestCount(2)
                .checkInDate(futureCheckIn)
                .checkOutDate(futureCheckOut)
                .breakfastIncluded(true)
                .totalPrice(new BigDecimal("905.00"))
                .build();

        when(bookingRepository.findById(42L)).thenReturn(Optional.of(booking));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        BookingResponse response = bookingService.getBooking(42L);

        assertThat(response.getBookingId()).isEqualTo(42L);
        assertThat(response.getGuest().getFirstName()).isEqualTo("John");
        assertThat(response.getRoom().getTitle()).isEqualTo("Deluxe Room");
        assertThat(response.getHotelContact().getName()).isEqualTo("Boutique Hotel Technikum");
    }

    @Test
    void getBooking_notFound_throwsDomainException() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.getBooking(99L))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.BOOKING_NOT_FOUND);
    }
}
