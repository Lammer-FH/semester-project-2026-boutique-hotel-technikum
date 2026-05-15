package at.fhtw.hotel.controller;

import at.fhtw.hotel.config.ApiRoutes;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import at.fhtw.hotel.service.AvailabilityService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AvailabilityController.class, properties = {"spring.jackson.property-naming-strategy=SNAKE_CASE"})
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AvailabilityService availabilityService;

    @Test
    void checkAvailability_whenAvailable_returnsAvailable() throws Exception {
        when(availabilityService.isRoomAvailable(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        mockMvc.perform(get(ApiRoutes.API + "/rooms/1/availability")
                        .param("check_in_date", "2026-06-01")
                        .param("check_out_date", "2026-06-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room_id").value(1))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.message").value("Room is available"));
    }

    @Test
    void checkAvailability_whenUnavailable_returnsUnavailable() throws Exception {
        when(availabilityService.isRoomAvailable(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        mockMvc.perform(get(ApiRoutes.API + "/rooms/1/availability")
                        .param("check_in_date", "2026-06-01")
                        .param("check_out_date", "2026-06-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.room_id").value(1))
                .andExpect(jsonPath("$.available").value(false))
                .andExpect(jsonPath("$.message").value("Room is unavailable"));
    }

    @Test
    void checkAvailability_invalidRoomId_returns404() throws Exception {
        when(availabilityService.isRoomAvailable(eq(99L), any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new DomainException(ErrorCode.ROOM_NOT_FOUND, "Room not found"));

        mockMvc.perform(get(ApiRoutes.API + "/rooms/99/availability")
                        .param("check_in_date", "2026-06-01")
                        .param("check_out_date", "2026-06-05"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("ROOM_NOT_FOUND"));
    }
}
