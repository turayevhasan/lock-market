package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeLong;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promo_code")
public class PromoCode extends TimeLong {
    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private Long discountPrice;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = Boolean.TRUE;
}
