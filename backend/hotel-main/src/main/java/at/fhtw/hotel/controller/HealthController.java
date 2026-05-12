package at.fhtw.hotel.controller;

import at.fhtw.hotel.util.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private static final Logger log = Logger.get(HealthController.class);

    @GetMapping({"/", "/health"})
    public ResponseEntity<String> health() {
        log.debug("Health check");
        return ResponseEntity.ok("OK");
    }
}
