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
    private String photoPath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Lock> locks;

    public Category(String name, String photo) {
        this.name = name;
        this.photoPath = photo;
    }
}
