package at.fhtw.hotel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
}
