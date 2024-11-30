package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;
import uz.pdp.lock_market.payload.feature.req.LockSizeDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lock_size")
public class LockSize extends TimeLong {
    @Column(nullable = false)
    private Double a;

    @Column(nullable = false)
    private Double b;

    @Column(nullable = false)
    private Double c;


    public LockSize(LockSizeDto req) {
        this.a = req.getA();
        this.b = req.getB();
        this.c = req.getC();
    }

    public LockSizeDto getDto() {
        return new LockSizeDto(a, b, c);
    }
}
