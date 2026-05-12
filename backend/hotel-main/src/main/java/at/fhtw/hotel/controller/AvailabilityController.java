package at.fhtw.hotel.controller;

import at.fhtw.hotel.dto.response.AvailabilityResponse;
import at.fhtw.hotel.service.AvailabilityService;
import at.fhtw.hotel.util.Logger;
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
public class AvailabilityController {

    private static final Logger log = Logger.get(AvailabilityController.class);

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public AvailabilityResponse checkAvailability(
            @PathVariable long roomId,
            @RequestParam("check_in_date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("check_out_date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        boolean available = availabilityService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        String message = available ? "Room is available" : "Room is unavailable";
        log.debug("Availability roomId={} available={}", roomId, available);
        return AvailabilityResponse.builder()
                .roomId(roomId)
                .isAvailable(available)
                .message(message)
                .build();
    }
}
