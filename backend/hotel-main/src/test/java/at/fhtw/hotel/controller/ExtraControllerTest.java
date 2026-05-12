package at.fhtw.hotel.controller;

import at.fhtw.hotel.model.Extra;
import at.fhtw.hotel.service.ExtraService;
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

@WebMvcTest(value = ExtraController.class, properties = {"spring.jackson.property-naming-strategy=SNAKE_CASE"})
class ExtraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExtraService extraService;

    @Test
    void listExtras_returnsExtraList() throws Exception {
        Extra wifi = Extra.builder().id(1L).code("wifi").title("WiFi").description("Free WiFi").iconName("wifi").build();
        Extra breakfast = Extra.builder().id(2L).code("breakfast").title("Breakfast").description("Buffet").iconName("coffee").build();

        when(extraService.listExtras()).thenReturn(List.of(wifi, breakfast));

        mockMvc.perform(get("/extras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("wifi"))
                .andExpect(jsonPath("$[0].icon_name").value("wifi"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].code").value("breakfast"));
    }

    @Test
    void listExtras_emptyList_returnsEmptyArray() throws Exception {
        when(extraService.listExtras()).thenReturn(List.of());
        mockMvc.perform(get("/extras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
