package at.fhtw.hotel.config;

import at.fhtw.hotel.mapper.BookingMapper;
import at.fhtw.hotel.mapper.ExtraMapper;
import at.fhtw.hotel.mapper.RoomMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    @Bean
    public RoomMapper roomMapper() {
        return new RoomMapper();
    }

    @Bean
    public ExtraMapper extraMapper() {
        return new ExtraMapper();
    }

    @Bean
    public BookingMapper bookingMapper() {
        return new BookingMapper();
    }
}
