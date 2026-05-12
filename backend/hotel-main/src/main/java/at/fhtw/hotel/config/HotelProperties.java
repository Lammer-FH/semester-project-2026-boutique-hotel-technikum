package at.fhtw.hotel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.hotel")
public class HotelProperties {

    private String name;
    private String email;
    private String phone;
    private Address address = new Address();
    private Directions directions = new Directions();
    private double breakfastPricePerPerson;

    @Getter
    @Setter
    public static class Address {
        private String street;
        private String city;
        private String postalCode;
        private String country;
    }

    @Getter
    @Setter
    public static class Directions {
        private String byTrain;
        private String byCar;
        private String parking;
    }
}
