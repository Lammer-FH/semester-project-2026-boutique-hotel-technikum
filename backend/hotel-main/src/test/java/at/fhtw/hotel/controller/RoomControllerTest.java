package at.fhtw.hotel.controller;

import at.fhtw.hotel.config.ApiRoutes;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.controller.dto.response.RoomResponse;
import at.fhtw.hotel.controller.mapper.RoomResponseMapper;
import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.service.RoomService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RoomController.class, properties = {"spring.jackson.property-naming-strategy=SNAKE_CASE"})
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private RoomResponseMapper roomResponseMapper;

    @Test
    void listRooms_returnsPaginatedResponse() throws Exception {
        Room room = new Room(1L, "Deluxe Room", "Bright room", 2,
                new BigDecimal("129.00"), List.of(), List.of());

        when(roomService.listRooms(0, 5, null, null)).thenReturn(List.of(room));
        when(roomService.countRooms(null, null)).thenReturn(1L);
        when(roomResponseMapper.toResponse(any(Room.class)))
                .thenAnswer(invocation -> {
                    Room r = invocation.getArgument(0);
                    return RoomResponse.builder()
                            .id(r.id()).title(r.title()).description(r.description())
                            .maxGuests(r.maxGuests()).basePricePerNight(r.basePricePerNight())
                            .images(List.of()).extras(List.of())
                            .build();
                });

        mockMvc.perform(get(ApiRoutes.API + "/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Deluxe Room"))
                .andExpect(jsonPath("$.pagination.page").value(1))
                .andExpect(jsonPath("$.pagination.size").value(5))
                .andExpect(jsonPath("$.pagination.total").value(1));
    }

    @Test
    void listRooms_withCustomPagination() throws Exception {
        when(roomService.listRooms(0, 3, null, null)).thenReturn(List.of());
        when(roomService.countRooms(null, null)).thenReturn(0L);

        mockMvc.perform(get(ApiRoutes.API + "/rooms").param("page", "1").param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pagination.page").value(1))
                .andExpect(jsonPath("$.pagination.size").value(3));
    }

    @Test
    void listRooms_withDateFilter_returnsAvailableRooms() throws Exception {
        Room room = new Room(2L, "Garden Suite", "Quiet suite", 3,
                new BigDecimal("199.00"), List.of(), List.of());
        LocalDate checkIn = LocalDate.parse("2026-07-01");
        LocalDate checkOut = LocalDate.parse("2026-07-05");

        when(roomService.listRooms(0, 5, checkIn, checkOut)).thenReturn(List.of(room));
        when(roomService.countRooms(checkIn, checkOut)).thenReturn(1L);
        when(roomResponseMapper.toResponse(any(Room.class)))
                .thenAnswer(invocation -> {
                    Room r = invocation.getArgument(0);
                    return RoomResponse.builder()
                            .id(r.id()).title(r.title()).description(r.description())
                            .maxGuests(r.maxGuests()).basePricePerNight(r.basePricePerNight())
                            .images(List.of()).extras(List.of())
                            .build();
                });

        mockMvc.perform(get(ApiRoutes.API + "/rooms")
                        .param("check_in_date", "2026-07-01")
                        .param("check_out_date", "2026-07-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(2))
                .andExpect(jsonPath("$.pagination.total").value(1));
    }

    @Test
    void listRooms_withOnlyCheckInDate_returns400() throws Exception {
        mockMvc.perform(get(ApiRoutes.API + "/rooms").param("check_in_date", "2026-07-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("INVALID_DATE_RANGE"));
    }

    @Test
    void getRoom_withValidId_returnsRoom() throws Exception {
        Room room = new Room(1L, "Deluxe Room", "Bright room", 2,
                new BigDecimal("129.00"), List.of(), List.of());

        when(roomService.getRoom(1L)).thenReturn(room);
        when(roomResponseMapper.toResponse(any(Room.class)))
                .thenAnswer(invocation -> {
                    Room r = invocation.getArgument(0);
                    return RoomResponse.builder()
                            .id(r.id()).title(r.title()).description(r.description())
                            .maxGuests(r.maxGuests()).basePricePerNight(r.basePricePerNight())
                            .images(List.of()).extras(List.of())
                            .build();
                });

        mockMvc.perform(get(ApiRoutes.API + "/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Deluxe Room"))
                .andExpect(jsonPath("$.max_guests").value(2));
    }

    @Test
    void getRoom_withInvalidId_returns404() throws Exception {
        when(roomService.getRoom(99L))
                .thenThrow(new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));

        mockMvc.perform(get(ApiRoutes.API + "/rooms/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("ROOM_NOT_FOUND"));
    }

    @Test
    void listRooms_withPageZero_returns400() throws Exception {
        mockMvc.perform(get(ApiRoutes.API + "/rooms").param("page", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("INVALID_INPUT"));
    }

    @Test
    void listRooms_withSizeAboveMax_returns400() throws Exception {
        mockMvc.perform(get(ApiRoutes.API + "/rooms").param("size", "6"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("INVALID_INPUT"));
    }

    @Test
    void listRooms_withSizeZero_returns400() throws Exception {
        mockMvc.perform(get(ApiRoutes.API + "/rooms").param("size", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("INVALID_INPUT"));
    }
}
