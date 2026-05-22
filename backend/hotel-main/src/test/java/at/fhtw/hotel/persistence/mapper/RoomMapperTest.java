package at.fhtw.hotel.persistence.mapper;

import at.fhtw.hotel.domain.model.Room;
import at.fhtw.hotel.persistence.entity.ExtraEntity;
import at.fhtw.hotel.persistence.entity.RoomEntity;
import at.fhtw.hotel.persistence.entity.RoomImageEntity;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RoomMapperTest {

    private final RoomMapper mapper = new RoomMapper();

    @Test
    void toDomain_mapsImagesAndExtras() {
        RoomEntity room = new RoomEntity();
        room.setId(1L);
        room.setTitle("Deluxe Room");
        room.setDescription("Bright room");
        room.setMaxGuests(2);
        room.setBasePricePerNight(new BigDecimal("129.00"));

        RoomImageEntity image = new RoomImageEntity();
        image.setId(10L);
        image.setRoom(room);
        image.setFileName("main.jpg");
        image.setAltText("Main image");
        image.setSortOrder(0);
        room.getImages().add(image);

        ExtraEntity extra = new ExtraEntity();
        extra.setId(5L);
        extra.setCode("wifi");
        extra.setTitle("WiFi");
        extra.setDescription("Free WiFi");
        extra.setIconName("wifi");

        room.getExtras().add(extra);

        Room result = mapper.toDomain(room);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Deluxe Room");
        assertThat(result.getImages()).hasSize(1);
        assertThat(result.getImages().getFirst().getFileName()).isEqualTo("main.jpg");
        assertThat(result.getExtras()).hasSize(1);
        assertThat(result.getExtras().getFirst().getCode()).isEqualTo("wifi");
    }
}
