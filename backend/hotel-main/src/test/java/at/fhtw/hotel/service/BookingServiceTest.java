package at.fhtw.hotel.service;

import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.controller.dto.request.BookingRequest;
import at.fhtw.hotel.controller.dto.response.BookingResponse;
import at.fhtw.hotel.controller.mapper.BookingResponseMapper;
import at.fhtw.hotel.domain.model.Booking;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.entity.BookingEntity;
import at.fhtw.hotel.persistence.mapper.BookingMapper;
import at.fhtw.hotel.persistence.mapper.RoomMapper;
import at.fhtw.hotel.persistence.repository.JpaBookingRepository;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import jakarta.persistence.EntityManager;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private JpaBookingRepository jpaBookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private JpaRoomRepository jpaRoomRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private RoomService roomService;

    @Mock
    private BookingResponseMapper bookingResponseMapper;

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

        bookingService = new BookingService(jpaBookingRepository, bookingMapper, entityManager,
                jpaRoomRepository, roomMapper, hotelProperties, roomService, bookingResponseMapper);
    }

    @Test
    void createBooking_success_returnsBookingResponse() {
        BookingResponse expectedResponse = BookingResponse.builder()
                .bookingId(42L)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .build();
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
        when(jpaBookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(false);

        BookingEntity savedEntity = new BookingEntity();
        savedEntity.setId(42L);
        when(jpaBookingRepository.save(any(BookingEntity.class))).thenReturn(savedEntity);

        Booking mappedBooking = Booking.builder()
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
        when(bookingMapper.toDomain(savedEntity)).thenReturn(mappedBooking);

        when(bookingResponseMapper.toResponse(any(), any(), any()))
                .thenReturn(expectedResponse);

        BookingResponse response = bookingService.createBooking(request);

        assertThat(response.getBookingId()).isEqualTo(42L);
        assertThat(response.getCheckInDate()).isEqualTo(checkIn);
        assertThat(response.getCheckOutDate()).isEqualTo(checkOut);
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
        when(jpaBookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(true);

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.DATES_UNAVAILABLE);
    }

    @Test
    void getBooking_returnsBookingResponse() {
        LocalDate today = LocalDate.now();
        LocalDate futureCheckIn = today.plusDays(10);
        LocalDate futureCheckOut = today.plusDays(15);

        BookingEntity entity = new BookingEntity();
        entity.setId(42L);
        HotelProperties.Address address = new HotelProperties.Address();
        address.setStreet("x");
        address.setCity("x");
        address.setPostalCode("x");
        address.setCountry("x");
        hotelProperties.setAddress(address);
        HotelProperties.Directions directions = new HotelProperties.Directions();
        directions.setByTrain("x");
        directions.setByCar("x");
        directions.setParking("x");
        hotelProperties.setDirections(directions);

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

        BookingResponse expectedResponse = BookingResponse.builder()
                .bookingId(42L)
                .build();

        when(jpaBookingRepository.findById(42L)).thenReturn(Optional.of(entity));
        when(bookingMapper.toDomain(entity)).thenReturn(booking);
        when(jpaRoomRepository.findById(1L)).thenReturn(Optional.of(new at.fhtw.hotel.persistence.entity.RoomEntity()));
        when(roomMapper.toDomain(any())).thenReturn(room);
        when(bookingResponseMapper.toResponse(any(), any(), any()))
                .thenReturn(expectedResponse);

        BookingResponse response = bookingService.getBooking(42L);

        assertThat(response.getBookingId()).isEqualTo(42L);
    }

    @Test
    void getBooking_notFound_throwsDomainException() {
        when(jpaBookingRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.getBooking(99L))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.BOOKING_NOT_FOUND);
    }

    @Test
    void createBooking_withPastDates_throwsDomainException() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("john@example.com")
                .guestCount(1)
                .checkInDate(LocalDate.of(2020, 1, 1))
                .checkOutDate(LocalDate.of(2020, 1, 5))
                .breakfastIncluded(false)
                .build();

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.INVALID_INPUT);
    }

    @Test
    void createBooking_withGuestCountZero_throwsDomainException() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("john@example.com")
                .guestCount(0)
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
    void createBooking_withoutBreakfast_calculatesCorrectPrice() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1L)
                .guestFirstName("John")
                .guestLastName("Doe")
                .guestEmail("john@example.com")
                .confirmEmail("john@example.com")
                .guestCount(1)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .breakfastIncluded(false)
                .build();

        BookingResponse expectedResponse = BookingResponse.builder()
                .bookingId(1L)
                .build();

        when(roomService.getRoom(1L)).thenReturn(room);
        when(jpaBookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(false);
        when(jpaBookingRepository.save(any(BookingEntity.class))).thenAnswer(returnsFirstArg());
        when(bookingMapper.toDomain(any())).thenAnswer(invocation -> {
            BookingEntity entity = invocation.getArgument(0);
            return Booking.builder()
                    .id(entity.getId())
                    .roomId(1L)
                    .guestFirstName("John")
                    .guestLastName("Doe")
                    .guestEmail("john@example.com")
                    .guestCount(entity.getGuestCount())
                    .checkInDate(entity.getCheckInDate())
                    .checkOutDate(entity.getCheckOutDate())
                    .breakfastIncluded(entity.isBreakfastIncluded())
                    .totalPrice(entity.getTotalPrice())
                    .build();
        });
        when(bookingResponseMapper.toResponse(any(), any(), any()))
                .thenReturn(expectedResponse);

        ArgumentCaptor<BookingEntity> captor = ArgumentCaptor.forClass(BookingEntity.class);
        bookingService.createBooking(request);
        verify(jpaBookingRepository).save(captor.capture());

        BookingEntity captured = captor.getValue();
        assertThat(captured.isBreakfastIncluded()).isFalse();
        assertThat(captured.getTotalPrice()).isEqualByComparingTo("645.00");
    }
}
