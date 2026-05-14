package at.fhtw.hotel.controller;

import at.fhtw.hotel.controller.dto.response.PaginatedResponse;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.controller.mapper.RoomResponseMapper;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;
    private final RoomResponseMapper roomResponseMapper;

    public RoomController(RoomService roomService, RoomResponseMapper roomResponseMapper) {
        this.roomService = roomService;
        this.roomResponseMapper = roomResponseMapper;
    }

    @GetMapping
    public PaginatedResponse<RoomResponse> listRooms(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(5) int size) {
        int zeroBasedPage = page - 1;
        List<Room> rooms = roomService.listRooms(zeroBasedPage, size);
        long total = roomService.countRooms();
        long totalPages = (long) Math.ceil((double) total / size);
        List<RoomResponse> data = rooms.stream().map(roomResponseMapper::toResponse).toList();
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
    public RoomResponse getRoom(@PathVariable long roomId) {
        Room room = roomService.getRoom(roomId);
        return roomResponseMapper.toResponse(room);
    }
}
