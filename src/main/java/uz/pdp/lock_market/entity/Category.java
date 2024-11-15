package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeLong;

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
    private String name;
    private UUID photoId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Lock> locks;

    public Category(String name, UUID photoId) {
        this.name = name;
        this.photoId = photoId;
    }
}
