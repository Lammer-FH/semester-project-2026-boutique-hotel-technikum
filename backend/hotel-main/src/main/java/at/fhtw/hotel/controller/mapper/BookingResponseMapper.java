package at.fhtw.hotel.controller.mapper;

import at.fhtw.hotel.application.dto.BookingResult;
import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.controller.dto.response.BookingResponse;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.repository.JpaRoomRepository;
import at.fhtw.hotel.persistence.mapper.RoomMapper;
import org.springframework.stereotype.Component;

@Component
public class BookingResponseMapper {

    private final HotelProperties hotelProperties;
    private final RoomResponseMapper roomResponseMapper;
    private final JpaRoomRepository jpaRoomRepository;
    private final RoomMapper roomMapper;

    public BookingResponseMapper(HotelProperties hotelProperties,
                                 RoomResponseMapper roomResponseMapper,
                                 JpaRoomRepository jpaRoomRepository,
                                 RoomMapper roomMapper) {
        this.hotelProperties = hotelProperties;
        this.roomResponseMapper = roomResponseMapper;
        this.jpaRoomRepository = jpaRoomRepository;
        this.roomMapper = roomMapper;
    }

    public BookingResponse toResponse(BookingResult result) {
        Room room = jpaRoomRepository.findById(result.roomId())
                .map(roomMapper::toDomain)
                .orElse(null);
        RoomResponse roomResponse = room != null ? roomResponseMapper.toResponse(room) : null;

        BookingResponse.PriceBreakdown breakdown = BookingResponse.PriceBreakdown.builder()
                .nights(result.nights())
                .roomRatePerNight(result.roomRatePerNight())
                .breakfastRate(result.breakfastRate())
                .breakfastPerPersonPerDay(result.breakfastPerPersonPerDay())
                .build();

        return BookingResponse.builder()
                .bookingId(result.bookingId())
                .checkInDate(result.checkInDate())
                .checkOutDate(result.checkOutDate())
                .breakfastIncluded(result.breakfastIncluded())
                .guestCount(result.guestCount())
                .totalPrice(result.totalPrice())
                .priceBreakdown(breakdown)
                .guest(BookingResponse.Guest.builder()
                        .firstName(result.guestFirstName())
                        .lastName(result.guestLastName())
                        .email(result.guestEmail())
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
                        .latitude(hotelProperties.getAddress().getLatitude())
                        .longitude(hotelProperties.getAddress().getLongitude())
                        .build())
                .directions(BookingResponse.Directions.builder()
                        .byTrain(hotelProperties.getDirections().getByTrain())
                        .byCar(hotelProperties.getDirections().getByCar())
                        .parking(hotelProperties.getDirections().getParking())
                        .build())
                .build();
    }
}
