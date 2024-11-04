package uz.pdp.lock_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeLong;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "auth_role")
public class Role extends TimeLong {
    private String name;

    public Role(String name) {
        this.name = name;
    }

}
