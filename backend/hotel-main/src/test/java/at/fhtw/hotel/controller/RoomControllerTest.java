package at.fhtw.hotel.controller;

import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.dto.response.PaginatedResponse;
import at.fhtw.hotel.dto.response.RoomResponse;
import at.fhtw.hotel.model.Room;
import at.fhtw.hotel.service.RoomService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
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

    @Test
    void listRooms_returnsPaginatedResponse() throws Exception {
        Room room = Room.builder()
                .id(1L)
                .title("Deluxe Room")
                .description("Bright room")
                .maxGuests(2)
                .basePricePerNight(new BigDecimal("129.00"))
                .images(List.of())
                .extras(List.of())
                .build();

        when(roomService.listRooms(0, 5)).thenReturn(List.of(room));
        when(roomService.countRooms()).thenReturn(1L);

        mockMvc.perform(get("/rooms"))
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
        when(roomService.listRooms(0, 3)).thenReturn(List.of());
        when(roomService.countRooms()).thenReturn(0L);

        mockMvc.perform(get("/rooms").param("page", "1").param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pagination.page").value(1))
                .andExpect(jsonPath("$.pagination.size").value(3));
    }

    @Test
    void getRoom_withValidId_returnsRoom() throws Exception {
        Room room = Room.builder()
                .id(1L)
                .title("Deluxe Room")
                .description("Bright room")
                .maxGuests(2)
                .basePricePerNight(new BigDecimal("129.00"))
                .images(List.of())
                .extras(List.of())
                .build();

        when(roomService.getRoom(1L)).thenReturn(room);

        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Deluxe Room"))
                .andExpect(jsonPath("$.max_guests").value(2));
    }

    @Test
    void getRoom_withInvalidId_returns404() throws Exception {
        when(roomService.getRoom(99L))
                .thenThrow(new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));

        mockMvc.perform(get("/rooms/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("ROOM_NOT_FOUND"));
    }
}
