package at.fhtw.hotel.controller;

import at.fhtw.hotel.config.ApiRoutes;
import at.fhtw.hotel.controller.dto.request.BookingRequest;
import at.fhtw.hotel.controller.dto.response.BookingResponse;
import at.fhtw.hotel.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRoutes.API + "/bookings")
@Tag(name = "Bookings", description = "Create and retrieve hotel bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(summary = "Create a booking", description = "Creates a new room booking with guest details, dates, and optional breakfast.")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input or email mismatch", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.controller.dto.response.ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.controller.dto.response.ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Room not available for selected dates", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.controller.dto.response.ErrorResponse.class)))
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        log.debug("Creating booking for roomId={}", request.getRoomId());
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get a booking", description = "Returns the details of an existing booking by its ID.")
    @ApiResponse(responseCode = "200", description = "Booking details")
    @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.controller.dto.response.ErrorResponse.class)))
    public BookingResponse getBooking(@Parameter(description = "Booking ID") @PathVariable long bookingId) {
        return bookingService.getBooking(bookingId);
    }
}
