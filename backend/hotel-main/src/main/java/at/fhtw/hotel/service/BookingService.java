package at.fhtw.hotel.service;

import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.dto.request.BookingRequest;
import at.fhtw.hotel.dto.response.BookingResponse;
import at.fhtw.hotel.dto.response.RoomResponse;
import at.fhtw.hotel.model.Booking;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.repository.BookingRepository;
import at.fhtw.hotel.repository.RoomRepository;
import at.fhtw.hotel.util.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private static final Logger log = Logger.get(BookingService.class);

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final HotelProperties hotelProperties;
    private final RoomService roomService;

    public BookingService(BookingRepository bookingRepository,
                          RoomRepository roomRepository,
                          HotelProperties hotelProperties,
                          RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.hotelProperties = hotelProperties;
        this.roomService = roomService;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.debug("Creating booking for roomId={} checkIn={} checkOut={}",
                request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());
        validateEmails(request);
        validateDates(request.getCheckInDate(), request.getCheckOutDate());
        Room room = roomService.getRoom(request.getRoomId());
        validateGuestCount(request.getGuestCount(), room.getMaxGuests());
        if (bookingRepository.existsOverlappingBooking(room.getId(), request.getCheckInDate(), request.getCheckOutDate())) {
            throw new DomainException(ErrorCode.DATES_UNAVAILABLE, "Requested dates are unavailable");
        }

        int nights = (int) ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal roomRate = room.getBasePricePerNight();
        BigDecimal breakfastPerPerson = BigDecimal.valueOf(hotelProperties.getBreakfastPricePerPerson());
        BigDecimal breakfastRate = request.isBreakfastIncluded()
                ? breakfastPerPerson.multiply(BigDecimal.valueOf(request.getGuestCount())).multiply(BigDecimal.valueOf(nights))
                : BigDecimal.ZERO;
        BigDecimal totalPrice = roomRate.multiply(BigDecimal.valueOf(nights)).add(breakfastRate);

        Booking booking = Booking.builder()
                .roomId(room.getId())
                .guestFirstName(request.getGuestFirstName())
                .guestLastName(request.getGuestLastName())
                .guestEmail(request.getGuestEmail())
                .guestCount(request.getGuestCount())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .breakfastIncluded(request.isBreakfastIncluded())
                .totalPrice(totalPrice)
                .build();

        Booking saved = bookingRepository.save(booking);
        return BookingResponseMapper.toResponse(saved, room, hotelProperties, nights, roomRate, breakfastRate, breakfastPerPerson);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBooking(long bookingId) {
        log.debug("Fetching booking bookingId={}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DomainException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found"));
        Room room = roomRepository.findById(booking.getRoomId())
                .orElseThrow(() -> new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));

        int nights = (int) ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        BigDecimal roomRate = room.getBasePricePerNight();
        BigDecimal breakfastPerPerson = BigDecimal.valueOf(hotelProperties.getBreakfastPricePerPerson());
        BigDecimal breakfastRate = booking.isBreakfastIncluded()
                ? breakfastPerPerson.multiply(BigDecimal.valueOf(booking.getGuestCount())).multiply(BigDecimal.valueOf(nights))
                : BigDecimal.ZERO;

        return BookingResponseMapper.toResponse(booking, room, hotelProperties, nights, roomRate, breakfastRate, breakfastPerPerson);
    }

    private void validateEmails(BookingRequest request) {
        if (!request.getGuestEmail().equalsIgnoreCase(request.getConfirmEmail())) {
            throw new DomainException(ErrorCode.EMAIL_MISMATCH, "Email confirmation does not match");
        }
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

    static class BookingResponseMapper {

        static BookingResponse toResponse(Booking booking,
                                          Room room,
                                          HotelProperties hotelProperties,
                                          int nights,
                                          BigDecimal roomRate,
                                          BigDecimal breakfastRate,
                                          BigDecimal breakfastPerPerson) {
            RoomResponse roomResponse = RoomResponseMapper.toResponse(room);
            return BookingResponse.builder()
                    .bookingId(booking.getId())
                    .checkInDate(booking.getCheckInDate())
                    .checkOutDate(booking.getCheckOutDate())
                    .breakfastIncluded(booking.isBreakfastIncluded())
                    .totalPrice(booking.getTotalPrice())
                    .priceBreakdown(BookingResponse.PriceBreakdown.builder()
                            .nights(nights)
                            .roomRate(roomRate)
                            .breakfastRate(breakfastRate)
                            .breakfastPerPersonPerDay(breakfastPerPerson)
                            .build())
                    .guest(BookingResponse.Guest.builder()
                            .firstName(booking.getGuestFirstName())
                            .lastName(booking.getGuestLastName())
                            .email(booking.getGuestEmail())
                            .build())
                    .room(roomResponse)
                    .hotelContact(BookingResponse.HotelContact.builder()
                            .name(hotelProperties.getName())
                            .street(hotelProperties.getAddress().getStreet())
                            .city(hotelProperties.getAddress().getCity())
                            .postalCode(hotelProperties.getAddress().getPostalCode())
                            .country(hotelProperties.getAddress().getCountry())
                            .email(hotelProperties.getEmail())
                            .phone(hotelProperties.getPhone())
                            .build())
                    .directions(BookingResponse.Directions.builder()
                            .byTrain(hotelProperties.getDirections().getByTrain())
                            .byCar(hotelProperties.getDirections().getByCar())
                            .parking(hotelProperties.getDirections().getParking())
                            .build())
                    .build();
        }
    }
}
