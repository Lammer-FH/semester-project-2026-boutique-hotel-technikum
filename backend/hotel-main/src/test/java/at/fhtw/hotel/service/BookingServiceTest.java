package at.fhtw.hotel.service;

import at.fhtw.hotel.application.dto.BookingResult;
import at.fhtw.hotel.application.dto.CreateBookingCommand;
import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
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

    private HotelProperties hotelProperties;
    private BookingService bookingService;

    private final Room room = new Room(
            1L, "Deluxe Room", "Bright room with city view", 2,
            new BigDecimal("129.00"), List.of(), List.of());

    private final LocalDate checkIn = LocalDate.of(2026, 6, 1);
    private final LocalDate checkOut = LocalDate.of(2026, 6, 6);

    @BeforeEach
    void setUp() {
        hotelProperties = new HotelProperties();
        hotelProperties.setName("Boutique Hotel Technikum");
        hotelProperties.setEmail("contact@hotel.com");
        hotelProperties.setPhone("+43 1 234567");
        hotelProperties.setBreakfastPricePerPerson(new BigDecimal("26.00"));

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
                jpaRoomRepository, roomMapper, hotelProperties, roomService);
    }

    @Test
    void createBooking_success_returnsBookingResult() {
        CreateBookingCommand command = new CreateBookingCommand(
                1L, "John", "Doe", "john@example.com", 2,
                checkIn, checkOut, true);

        when(roomService.getRoom(1L)).thenReturn(room);
        when(jpaBookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(false);

        BookingEntity savedEntity = new BookingEntity();
        savedEntity.setId(42L);
        when(jpaBookingRepository.save(any(BookingEntity.class))).thenReturn(savedEntity);

        Booking mappedBooking = new Booking(
                42L, 1L, "John", "Doe", "john@example.com", 2,
                checkIn, checkOut, true, new BigDecimal("905.00"),
                5, new BigDecimal("129.00"), new BigDecimal("260.00"), new BigDecimal("26.00"));
        when(bookingMapper.toDomain(savedEntity)).thenReturn(mappedBooking);

        BookingResult result = bookingService.createBooking(command);

        assertThat(result.bookingId()).isEqualTo(42L);
        assertThat(result.checkInDate()).isEqualTo(checkIn);
        assertThat(result.checkOutDate()).isEqualTo(checkOut);
        assertThat(result.nights()).isEqualTo(5);
        assertThat(result.roomRatePerNight()).isEqualByComparingTo("129.00");
        assertThat(result.breakfastRate()).isEqualByComparingTo("260.00");
        assertThat(result.totalPrice()).isEqualByComparingTo("905.00");
    }

    @Test
    void createBooking_guestCountExceedsMax_throwsDomainException() {
        CreateBookingCommand command = new CreateBookingCommand(
                1L, "John", "Doe", "john@example.com", 5,
                checkIn, checkOut, false);

        when(roomService.getRoom(1L)).thenReturn(room);

        assertThatThrownBy(() -> bookingService.createBooking(command))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.INVALID_INPUT);
    }

    @Test
    void createBooking_datesUnavailable_throwsDomainException() {
        CreateBookingCommand command = new CreateBookingCommand(
                1L, "John", "Doe", "john@example.com", 2,
                checkIn, checkOut, false);

        when(roomService.getRoom(1L)).thenReturn(room);
        when(jpaBookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(true);

        assertThatThrownBy(() -> bookingService.createBooking(command))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.DATES_UNAVAILABLE);
    }

    @Test
    void getBooking_returnsBookingResult() {
        LocalDate today = LocalDate.now();
        LocalDate futureCheckIn = today.plusDays(10);
        LocalDate futureCheckOut = today.plusDays(15);

        BookingEntity entity = new BookingEntity();
        entity.setId(42L);

        Booking booking = new Booking(
                42L, 1L, "John", "Doe", "john@example.com", 2,
                futureCheckIn, futureCheckOut, true, new BigDecimal("905.00"),
                5, new BigDecimal("129.00"), new BigDecimal("260.00"), new BigDecimal("26.00"));

        when(jpaBookingRepository.findById(42L)).thenReturn(Optional.of(entity));
        when(bookingMapper.toDomain(entity)).thenReturn(booking);

        BookingResult result = bookingService.getBooking(42L);

        assertThat(result.bookingId()).isEqualTo(42L);
        assertThat(result.nights()).isEqualTo(5);
        assertThat(result.roomRatePerNight()).isEqualByComparingTo("129.00");
        assertThat(result.breakfastRate()).isEqualByComparingTo("260.00");
        assertThat(result.totalPrice()).isEqualByComparingTo("905.00");
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
        CreateBookingCommand command = new CreateBookingCommand(
                1L, "John", "Doe", "john@example.com", 1,
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), false);

        assertThatThrownBy(() -> bookingService.createBooking(command))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.INVALID_INPUT);
    }

    @Test
    void createBooking_withGuestCountZero_throwsDomainException() {
        CreateBookingCommand command = new CreateBookingCommand(
                1L, "John", "Doe", "john@example.com", 0,
                checkIn, checkOut, false);

        when(roomService.getRoom(1L)).thenReturn(room);

        assertThatThrownBy(() -> bookingService.createBooking(command))
                .isInstanceOf(DomainException.class)
                .hasFieldOrPropertyWithValue("code", ErrorCode.INVALID_INPUT);
    }

    @Test
    void createBooking_withoutBreakfast_storesCorrectBreakdown() {
        CreateBookingCommand command = new CreateBookingCommand(
                1L, "John", "Doe", "john@example.com", 1,
                checkIn, checkOut, false);

        when(roomService.getRoom(1L)).thenReturn(room);
        when(jpaBookingRepository.existsOverlappingBooking(1L, checkIn, checkOut)).thenReturn(false);
        when(jpaBookingRepository.save(any(BookingEntity.class))).thenAnswer(invocation -> {
            BookingEntity e = invocation.getArgument(0);
            e.setId(1L);
            return e;
        });
        when(bookingMapper.toDomain(any())).thenAnswer(invocation -> {
            BookingEntity e = invocation.getArgument(0);
            return new Booking(
                    e.getId(), 1L, "John", "Doe", "john@example.com",
                    e.getGuestCount(), e.getCheckInDate(), e.getCheckOutDate(),
                    e.isBreakfastIncluded(), e.getTotalPrice(),
                    e.getNights(), e.getRoomRatePerNight(),
                    e.getBreakfastRate(), e.getBreakfastPerPersonPerDay());
        });

        ArgumentCaptor<BookingEntity> captor = ArgumentCaptor.forClass(BookingEntity.class);
        bookingService.createBooking(command);
        verify(jpaBookingRepository).save(captor.capture());

        BookingEntity captured = captor.getValue();
        assertThat(captured.isBreakfastIncluded()).isFalse();
        assertThat(captured.getTotalPrice()).isEqualByComparingTo("645.00");
        assertThat(captured.getNights()).isEqualTo(5);
        assertThat(captured.getRoomRatePerNight()).isEqualByComparingTo("129.00");
        assertThat(captured.getBreakfastRate()).isEqualByComparingTo("0.00");
        assertThat(captured.getBreakfastPerPersonPerDay()).isEqualByComparingTo("26.00");
    }
}
