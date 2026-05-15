package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.persistence.entity.ExtraEntity;
import at.fhtw.hotel.persistence.mapper.ExtraMapper;
import at.fhtw.hotel.persistence.repository.JpaExtraRepository;
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
    private JpaExtraRepository jpaExtraRepository;

    @Mock
    private ExtraMapper extraMapper;

    @InjectMocks
    private ExtraService extraService;

    @Test
    void listExtras_returnsAllExtras() {
        ExtraEntity entity1 = new ExtraEntity();
        ExtraEntity entity2 = new ExtraEntity();
        Extra extra1 = Extra.builder().id(1L).code("wifi").title("WiFi").description("Free WiFi").iconName("wifi").build();
        Extra extra2 = Extra.builder().id(2L).code("breakfast").title("Breakfast").description("Buffet").iconName("coffee").build();

        when(jpaExtraRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(extraMapper.toDomain(entity1)).thenReturn(extra1);
        when(extraMapper.toDomain(entity2)).thenReturn(extra2);

        assertThat(extraService.listExtras()).containsExactly(extra1, extra2);
    }
}
