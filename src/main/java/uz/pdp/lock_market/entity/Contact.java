package uz.pdp.lock_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact")
public class Contact extends TimeLong {
    private String name;
    private String email;
    private boolean read;
}
