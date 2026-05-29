package at.fhtw.hotel.service;

import at.fhtw.hotel.application.dto.BookingResult;
import at.fhtw.hotel.application.dto.CreateBookingCommand;
import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.domain.model.Booking;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.entity.BookingEntity;
import at.fhtw.hotel.persistence.entity.RoomEntity;
import at.fhtw.hotel.persistence.mapper.BookingMapper;
import at.fhtw.hotel.persistence.mapper.RoomMapper;
import at.fhtw.hotel.persistence.repository.JpaBookingRepository;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final JpaBookingRepository jpaBookingRepository;
    private final BookingMapper bookingMapper;
    private final EntityManager entityManager;
    private final JpaRoomRepository jpaRoomRepository;
    private final RoomMapper roomMapper;
    private final HotelProperties hotelProperties;
    private final RoomService roomService;

    public BookingService(JpaBookingRepository jpaBookingRepository,
                          BookingMapper bookingMapper,
                          EntityManager entityManager,
                          JpaRoomRepository jpaRoomRepository,
                          RoomMapper roomMapper,
                          HotelProperties hotelProperties,
                          RoomService roomService) {
        this.jpaBookingRepository = jpaBookingRepository;
        this.bookingMapper = bookingMapper;
        this.entityManager = entityManager;
        this.jpaRoomRepository = jpaRoomRepository;
        this.roomMapper = roomMapper;
        this.hotelProperties = hotelProperties;
        this.roomService = roomService;
    }

    @Transactional
    public BookingResult createBooking(CreateBookingCommand command) {
        log.debug("Creating booking for roomId={} checkIn={} checkOut={}",
                command.roomId(), command.checkInDate(), command.checkOutDate());
        DateRange dateRange = DateRange.of(command.checkInDate(), command.checkOutDate());
        Room room = roomService.getRoom(command.roomId());
        validateGuestCount(command.guestCount(), room.maxGuests());
        if (jpaBookingRepository.existsOverlappingBooking(room.id(), dateRange.checkIn(), dateRange.checkOut())) {
            throw new DomainException(ErrorCode.DATES_UNAVAILABLE, "Requested dates are unavailable");
        }

        PriceCalculation price = PriceCalculation.forBooking(
                room, dateRange, command.guestCount(), command.breakfastIncluded(),
                hotelProperties.getBreakfastPricePerPerson());

        BookingEntity entity = new BookingEntity();
        entity.setRoom(entityManager.getReference(RoomEntity.class, room.id()));
        entity.setGuestFirstName(command.guestFirstName());
        entity.setGuestLastName(command.guestLastName());
        entity.setGuestEmail(command.guestEmail());
        entity.setGuestCount(command.guestCount());
        entity.setCheckInDate(dateRange.checkIn());
        entity.setCheckOutDate(dateRange.checkOut());
        entity.setBreakfastIncluded(command.breakfastIncluded());
        entity.setTotalPrice(price.totalPrice());
        entity.setNights(price.nights());
        entity.setRoomRatePerNight(price.roomRatePerNight());
        entity.setBreakfastRate(price.breakfastRate());
        entity.setBreakfastPerPersonPerDay(price.breakfastPerPersonPerDay());
        BookingEntity saved = jpaBookingRepository.save(entity);

        return toResult(bookingMapper.toDomain(saved));
    }

    @Transactional(readOnly = true)
    public BookingResult getBooking(long bookingId) {
        log.debug("Fetching booking bookingId={}", bookingId);
        Booking booking = jpaBookingRepository.findById(bookingId)
                .map(bookingMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found"));
        return toResult(booking);
    }

    private BookingResult toResult(Booking booking) {
        return new BookingResult(
                booking.id(),
                booking.checkInDate(),
                booking.checkOutDate(),
                booking.breakfastIncluded(),
                booking.guestCount(),
                booking.totalPrice(),
                booking.nights(),
                booking.roomRatePerNight(),
                booking.breakfastRate(),
                booking.breakfastPerPersonPerDay(),
                booking.guestFirstName(),
                booking.guestLastName(),
                booking.guestEmail(),
                booking.roomId()
        );
    }

    private void validateGuestCount(int guestCount, int maxGuests) {
        if (guestCount < 1 || guestCount > maxGuests) {
            throw new DomainException(ErrorCode.INVALID_INPUT, "Guest count exceeds room capacity");
        }
    }

    public record DateRange(LocalDate checkIn, LocalDate checkOut) {
        public DateRange {
            if (!checkOut.isAfter(checkIn)) {
                throw new DomainException(ErrorCode.INVALID_DATE_RANGE, "Check-out must be after check-in");
            }
        }

        public static DateRange of(LocalDate checkIn, LocalDate checkOut) {
            DateRange range = new DateRange(checkIn, checkOut);
            if (checkIn.isBefore(LocalDate.now()) || checkOut.isBefore(LocalDate.now())) {
                throw new DomainException(ErrorCode.INVALID_INPUT, "Dates must be today or in the future");
            }
            return range;
        }
    }

    public record PriceCalculation(int nights, BigDecimal roomRatePerNight, BigDecimal breakfastRate, BigDecimal breakfastPerPersonPerDay, BigDecimal totalPrice) {
        public static PriceCalculation forBooking(Room room, DateRange dateRange, int guestCount, boolean breakfastIncluded, BigDecimal breakfastPricePerPerson) {
            int nights = (int) ChronoUnit.DAYS.between(dateRange.checkIn(), dateRange.checkOut());
            BigDecimal roomRatePerNight = room.basePricePerNight();
            BigDecimal breakfastRate = breakfastIncluded
                    ? breakfastPricePerPerson.multiply(BigDecimal.valueOf(guestCount)).multiply(BigDecimal.valueOf(nights))
                    : BigDecimal.ZERO;
            BigDecimal totalPrice = roomRatePerNight.multiply(BigDecimal.valueOf(nights)).add(breakfastRate);
            return new PriceCalculation(nights, roomRatePerNight, breakfastRate, breakfastPricePerPerson, totalPrice);
        }
    }
}
