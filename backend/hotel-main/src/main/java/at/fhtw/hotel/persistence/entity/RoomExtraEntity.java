package at.fhtw.hotel.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room_extras")
public class RoomExtraEntity {

    @EmbeddedId
    private RoomExtraId id = new RoomExtraId();

    @ManyToOne(optional = false)
    @MapsId("roomId")
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne(optional = false)
    @MapsId("extraId")
    @JoinColumn(name = "extra_id", nullable = false)
    private ExtraEntity extra;

    @Getter
    @Setter
    @EqualsAndHashCode
    @Embeddable
    public static class RoomExtraId implements Serializable {

        @Column(name = "room_id")
        private Long roomId;

        @Column(name = "extra_id")
        private Long extraId;
    }
}
