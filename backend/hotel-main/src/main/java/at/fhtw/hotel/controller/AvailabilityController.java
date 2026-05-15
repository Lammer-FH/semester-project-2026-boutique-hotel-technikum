package at.fhtw.hotel.controller;

import at.fhtw.hotel.controller.dto.response.AvailabilityResponse;
import at.fhtw.hotel.service.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/rooms/{roomId}/availability")
@Validated
@Tag(name = "Availability", description = "Check room availability for specific dates")
public class AvailabilityController {

    private static final Logger log = LoggerFactory.getLogger(AvailabilityController.class);

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    @Operation(summary = "Check room availability", description = "Checks whether a specific room is available for the given date range.")
    @ApiResponse(responseCode = "200", description = "Availability result")
    public AvailabilityResponse checkAvailability(
            @Parameter(description = "Room ID") @PathVariable long roomId,
            @Parameter(description = "Check-in date (ISO 8601)") @RequestParam("check_in_date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @Parameter(description = "Check-out date (ISO 8601)") @RequestParam("check_out_date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        boolean available = availabilityService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        String message = available ? "Room is available" : "Room is unavailable";
        log.debug("Availability roomId={} available={}", roomId, available);
        return AvailabilityResponse.builder()
                .roomId(roomId)
                .available(available)
                .message(message)
                .build();
    }
}
