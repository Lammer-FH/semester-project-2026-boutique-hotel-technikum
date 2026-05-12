package at.fhtw.hotel.controller;

import at.fhtw.hotel.dto.response.PaginatedResponse;
import at.fhtw.hotel.dto.response.RoomResponse;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.service.RoomResponseMapper;
import at.fhtw.hotel.service.RoomService;
import at.fhtw.hotel.util.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/rooms")
@Validated
@Tag(name = "Rooms", description = "Browse and retrieve hotel room details")
public class RoomController {

    private static final Logger log = Logger.get(RoomController.class);

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "List all rooms", description = "Returns a paginated list of available rooms with their details, images, and extras.")
    @ApiResponse(responseCode = "200", description = "Paginated list of rooms")
    public PaginatedResponse<RoomResponse> listRooms(
            @Parameter(description = "Page number (1-based)") @RequestParam(defaultValue = "1") @Min(1) int page,
            @Parameter(description = "Number of rooms per page (max 5)") @RequestParam(defaultValue = "5") @Min(1) @Max(5) int size) {
        int zeroBasedPage = page - 1;
        List<Room> rooms = roomService.listRooms(zeroBasedPage, size);
        long total = roomService.countRooms();
        long totalPages = (long) Math.ceil((double) total / size);
        List<RoomResponse> data = rooms.stream().map(RoomResponseMapper::toResponse).toList();
        log.debug("Rooms listed page={} size={}", page, size);
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
    @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(schema = @Schema(implementation = at.fhtw.hotel.dto.response.ErrorResponse.class)))
    public RoomResponse getRoom(@Parameter(description = "Room ID") @PathVariable long roomId) {
        Room room = roomService.getRoom(roomId);
        return RoomResponseMapper.toResponse(room);
    }
}
