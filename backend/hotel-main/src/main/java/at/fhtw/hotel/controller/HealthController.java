package at.fhtw.hotel.controller;

import at.fhtw.hotel.util.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Health check endpoints")
public class HealthController {

    private static final Logger log = Logger.get(HealthController.class);

    @GetMapping({"/", "/health"})
    @Operation(summary = "Health check", description = "Returns OK if the service is running.")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<String> health() {
        log.debug("Health check");
        return ResponseEntity.ok("OK");
    }
}
