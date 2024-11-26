package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;
import uz.pdp.lock_market.payload.feature.req.DoorWidthDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "door_width")
public class DoorWidth extends TimeLong {
    @Column(nullable = false)
    private Double a;

    @Column(nullable = false)
    private Double b;

    public DoorWidth(DoorWidthDto req) {
        this.a = req.getA();
        this.b = req.getB();
    }

    public DoorWidthDto getDto() {
        return new DoorWidthDto(this.a, this.b);
    }
}
