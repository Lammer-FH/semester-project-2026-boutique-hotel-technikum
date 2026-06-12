package at.fhtw.hotel.controller;

import at.fhtw.hotel.config.ApiRoutes;
import at.fhtw.hotel.controller.dto.response.PaginatedResponse;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.controller.mapper.RoomResponseMapper;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping(ApiRoutes.API + "/rooms")
@Validated
@Tag(name = "Rooms", description = "Browse and retrieve hotel room details")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;
    private final RoomResponseMapper roomResponseMapper;

    public RoomController(RoomService roomService, RoomResponseMapper roomResponseMapper) {
        this.roomService = roomService;
        this.roomResponseMapper = roomResponseMapper;
    }

    @GetMapping
    @Operation(summary = "List all rooms", description = "Returns a paginated list of rooms with their details, images, and extras. When both check_in_date and check_out_date are supplied, only rooms available for that range are returned.")
    @ApiResponse(responseCode = "200", description = "Paginated list of rooms")
    @ApiResponse(responseCode = "400", description = "Invalid pagination or date range", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.controller.dto.response.ErrorResponse.class)))
    public PaginatedResponse<RoomResponse> listRooms(
            @Parameter(description = "Page number (1-based)") @RequestParam(defaultValue = "1") @Min(1) int page,
            @Parameter(description = "Number of rooms per page (max 5)") @RequestParam(defaultValue = "5") @Min(1) @Max(5) int size,
            @Parameter(description = "Filter to rooms available from this check-in date (ISO 8601). Requires check_out_date.") @RequestParam(value = "check_in_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @Parameter(description = "Filter to rooms available until this check-out date (ISO 8601). Requires check_in_date.") @RequestParam(value = "check_out_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        if ((checkInDate == null) != (checkOutDate == null)) {
            throw new DomainException(ErrorCode.INVALID_DATE_RANGE,
                    "Both check_in_date and check_out_date are required to filter by availability");
        }
        int zeroBasedPage = page - 1;
        List<Room> rooms = roomService.listRooms(zeroBasedPage, size, checkInDate, checkOutDate);
        long total = roomService.countRooms(checkInDate, checkOutDate);
        long totalPages = (long) Math.ceil((double) total / size);
        List<RoomResponse> data = rooms.stream().map(roomResponseMapper::toResponse).toList();
        log.debug("Rooms listed page={} size={} checkIn={} checkOut={}", page, size, checkInDate, checkOutDate);
        return PaginatedResponse.<RoomResponse>builder()
                .data(data)
                .pagination(PaginatedResponse.Pagination.builder()
                        .page(page)
                        .size(size)
                        .total(total)
                        .totalPages(totalPages)
                        .build())
                .build();
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "Get a single room", description = "Returns detailed information about a specific room by its ID.")
    @ApiResponse(responseCode = "200", description = "Room details")
    @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.controller.dto.response.ErrorResponse.class)))
    public RoomResponse getRoom(@Parameter(description = "Room ID") @PathVariable long roomId) {
        Room room = roomService.getRoom(roomId);
        return roomResponseMapper.toResponse(room);
    }
}
