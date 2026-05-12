package at.fhtw.hotel.service;

import at.fhtw.hotel.model.Extra;
import at.fhtw.hotel.repository.ExtraRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtraServiceTest {

    @Mock
    private ExtraRepository extraRepository;

    @InjectMocks
    private ExtraService extraService;

    @Test
    void listExtras_returnsAllExtras() {
        List<Extra> expected = List.of(
                Extra.builder().id(1L).code("wifi").title("WiFi").description("Free WiFi").iconName("wifi").build(),
                Extra.builder().id(2L).code("breakfast").title("Breakfast").description("Buffet").iconName("coffee").build()
        );
        when(extraRepository.findAll()).thenReturn(expected);
        assertThat(extraService.listExtras()).isEqualTo(expected);
    }
}
