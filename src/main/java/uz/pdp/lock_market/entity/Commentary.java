package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeLong;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "commentary")
public class Commentary extends TimeLong {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lock lock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commentary")
    private List<Comment> comments;
}
