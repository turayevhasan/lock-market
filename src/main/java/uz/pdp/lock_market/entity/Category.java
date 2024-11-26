package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends TimeLong {
    @Column(unique = true, nullable = false)
    private String nameUz;

    @Column(unique = true, nullable = false)
    private String nameEn;

    @Column(unique = true, nullable = false)
    private String nameRu;

    private String photoPath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Lock> locks;
}
