package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "basket")
public class Basket extends TimeLong {
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Lock lock;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;
}
