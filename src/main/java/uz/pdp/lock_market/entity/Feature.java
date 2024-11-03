package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;
import uz.pdp.lock_market.entity.time.TimeLong;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "feature")
public class Feature extends TimeLong {
    @Column(nullable = false)
    private Integer memoryOfCards;

    @Column(nullable = false)
    private Boolean application;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Color> colors;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String battery;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UnlockType unlockType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DoorType doorType;

    @Column(nullable = false)
    private String doorWidth;

    @Column(nullable = false)
    private String lockSize;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String equipment;
}
