package at.fhtw.hotel.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.hotel")
public class HotelProperties {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @Valid
    private Address address = new Address();

    @Valid
    private Directions directions = new Directions();

    @Positive
    private double breakfastPricePerPerson;

    @Getter
    @Setter
    public static class Address {
        @NotBlank
        private String street;

        @NotBlank
        private String city;

        @NotBlank
        private String postalCode;

        @NotBlank
        private String country;
    }

    @Getter
    @Setter
    public static class Directions {
        @NotBlank
        private String byTrain;

        @NotBlank
        private String byCar;

        @NotBlank
        private String parking;
    }
}
