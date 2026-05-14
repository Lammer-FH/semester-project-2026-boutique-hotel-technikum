package at.fhtw.hotel.controller.mapper;

import at.fhtw.hotel.config.HotelProperties;
import at.fhtw.hotel.controller.dto.response.BookingResponse;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.domain.model.Booking;
import at.fhtw.hotel.domain.model.Room;
import org.springframework.stereotype.Component;

@Component
public class BookingResponseMapper {

    private final HotelProperties hotelProperties;
    private final RoomResponseMapper roomResponseMapper;

    public BookingResponseMapper(HotelProperties hotelProperties, RoomResponseMapper roomResponseMapper) {
        this.hotelProperties = hotelProperties;
        this.roomResponseMapper = roomResponseMapper;
    }

    public BookingResponse toResponse(Booking booking, Room room, BookingResponse.PriceBreakdown priceBreakdown) {
        RoomResponse roomResponse = roomResponseMapper.toResponse(room);
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .breakfastIncluded(booking.isBreakfastIncluded())
                .totalPrice(booking.getTotalPrice())
                .priceBreakdown(priceBreakdown)
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
