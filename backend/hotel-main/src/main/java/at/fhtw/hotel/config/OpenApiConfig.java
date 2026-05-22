package at.fhtw.hotel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${server.servlet.context-path}") String contextPath,
            HotelProperties hotel) {
        return new OpenAPI()
                .info(new Info()
                        .title(hotel.getName() + " API")
                        .version("1.0.0")
                        .description("""
                                RESTful API for the Boutique Hotel Technikum.
                                Enables frontend applications to browse rooms,
                                check availability, and create bookings.
                                """)
                        .contact(new Contact()
                                .name(hotel.getName())
                                .email(hotel.getEmail()))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://example.com")))
                .servers(List.of(
                        new Server()
                                .url(contextPath)
                                .description("Default server URL")));
    }
}
