package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;
import uz.pdp.lock_market.entity.base.TimeLong;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "feature")
public class Feature extends TimeLong {
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Lock lock;

    @Column(nullable = false)
    private Integer memoryOfCards;

    @Column(nullable = false)
    private Boolean application;

    @ElementCollection
    private List<Color> colors;

    @Column(nullable = false)
    private String materialUz;

    @Column(nullable = false)
    private String materialEn;

    @Column(nullable = false)
    private String materialRu;

    @ManyToOne(fetch = FetchType.LAZY)
    private Battery battery;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<UnlockType> unlockTypes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DoorType doorType;

    @ManyToOne(fetch = FetchType.LAZY)
    private DoorWidth doorWidth;

    @ManyToOne(fetch = FetchType.LAZY)
    private LockSize lockSize;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String equipmentUz;

    @Column(nullable = false)
    private String equipmentEn;

    @Column(nullable = false)
    private String equipmentRu;
}
