package at.fhtw.hotel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Binds properties from {@code app.hotel.*} in application.yaml.
 * <p>
 * Values are resolved from the YAML file at runtime; the
 * {@link ConfigurationProperties} annotation wires them automatically.
 */
@Component
@ConfigurationProperties(prefix = "app.hotel")
@Getter
@Setter
public class HotelProperties {
    private String name;
    private String email;
    private String phone;
    private BigDecimal breakfastPricePerPerson;

    private Address address;
    private Directions directions;

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
