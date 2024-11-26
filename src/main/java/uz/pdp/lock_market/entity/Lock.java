package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.enums.LockType;
import uz.pdp.lock_market.entity.base.TimeLong;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lock")
public class Lock extends TimeLong {
    @Column(nullable = false, unique = true)
    private String nameUz;

    @Column(nullable = false, unique = true)
    private String nameEn;

    @Column(nullable = false, unique = true)
    private String nameRu;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptionUz;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptionRu;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long newPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> photos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LockType lockType;

    @Column(nullable = false)
    private Boolean hasGift;

    /////////////////////////////////////////////////////

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lock")
    private List<Comment> comments;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lock")
    private Feature feature;
}
