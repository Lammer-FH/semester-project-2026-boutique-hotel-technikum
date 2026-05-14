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
    private final BookingResponseMapper bookingResponseMapper;

    public BookingService(JpaBookingRepository jpaBookingRepository,
                          BookingMapper bookingMapper,
                          EntityManager entityManager,
                          JpaRoomRepository jpaRoomRepository,
                          RoomMapper roomMapper,
                          HotelProperties hotelProperties,
                          RoomService roomService,
                          BookingResponseMapper bookingResponseMapper) {
        this.jpaBookingRepository = jpaBookingRepository;
        this.bookingMapper = bookingMapper;
        this.entityManager = entityManager;
        this.jpaRoomRepository = jpaRoomRepository;
        this.roomMapper = roomMapper;
        this.hotelProperties = hotelProperties;
        this.roomService = roomService;
        this.bookingResponseMapper = bookingResponseMapper;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.debug("Creating booking for roomId={} checkIn={} checkOut={}",
                request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());
        validateDates(request.getCheckInDate(), request.getCheckOutDate());
        Room room = roomService.getRoom(request.getRoomId());
        validateGuestCount(request.getGuestCount(), room.getMaxGuests());
        if (jpaBookingRepository.existsOverlappingBooking(room.getId(), request.getCheckInDate(), request.getCheckOutDate())) {
            throw new DomainException(ErrorCode.DATES_UNAVAILABLE, "Requested dates are unavailable");
        }

        int nights = (int) ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal roomRate = room.getBasePricePerNight();
        BigDecimal breakfastPerPerson = BigDecimal.valueOf(hotelProperties.getBreakfastPricePerPerson());
        BigDecimal breakfastRate = request.isBreakfastIncluded()
                ? breakfastPerPerson.multiply(BigDecimal.valueOf(request.getGuestCount())).multiply(BigDecimal.valueOf(nights))
                : BigDecimal.ZERO;
        BigDecimal totalPrice = roomRate.multiply(BigDecimal.valueOf(nights)).add(breakfastRate);

        BookingEntity entity = new BookingEntity();
        entity.setRoom(entityManager.getReference(RoomEntity.class, room.getId()));
        entity.setGuestFirstName(request.getGuestFirstName());
        entity.setGuestLastName(request.getGuestLastName());
        entity.setGuestEmail(request.getGuestEmail());
        entity.setGuestCount(request.getGuestCount());
        entity.setCheckInDate(request.getCheckInDate());
        entity.setCheckOutDate(request.getCheckOutDate());
        entity.setBreakfastIncluded(request.isBreakfastIncluded());
        entity.setTotalPrice(totalPrice);
        BookingEntity saved = jpaBookingRepository.save(entity);

        BookingResponse.PriceBreakdown breakdown = BookingResponse.PriceBreakdown.builder()
                .nights(nights)
                .roomRate(roomRate)
                .breakfastRate(breakfastRate)
                .breakfastPerPersonPerDay(breakfastPerPerson)
                .build();
        return bookingResponseMapper.toResponse(bookingMapper.toDomain(saved), room, breakdown);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBooking(long bookingId) {
        log.debug("Fetching booking bookingId={}", bookingId);
        Booking booking = jpaBookingRepository.findById(bookingId)
                .map(bookingMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found"));
        Room room = jpaRoomRepository.findById(booking.getRoomId())
                .map(roomMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));

        int nights = (int) ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        BigDecimal roomRate = room.getBasePricePerNight();
        BigDecimal breakfastPerPerson = BigDecimal.valueOf(hotelProperties.getBreakfastPricePerPerson());
        BigDecimal breakfastRate = booking.isBreakfastIncluded()
                ? breakfastPerPerson.multiply(BigDecimal.valueOf(booking.getGuestCount())).multiply(BigDecimal.valueOf(nights))
                : BigDecimal.ZERO;

        BookingResponse.PriceBreakdown breakdown = BookingResponse.PriceBreakdown.builder()
                .nights(nights)
                .roomRate(roomRate)
                .breakfastRate(breakfastRate)
                .breakfastPerPersonPerDay(breakfastPerPerson)
                .build();
        return bookingResponseMapper.toResponse(booking, room, breakdown);
    }

    private void validateGuestCount(int guestCount, int maxGuests) {
        if (guestCount < 1 || guestCount > maxGuests) {
            throw new DomainException(ErrorCode.INVALID_INPUT, "Guest count exceeds room capacity");
        }
    }

    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new DomainException(ErrorCode.INVALID_DATE_RANGE, "Check-out must be after check-in");
        }
        if (checkInDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(LocalDate.now())) {
            throw new DomainException(ErrorCode.INVALID_INPUT, "Dates must be today or in the future");
        }
    }

}
