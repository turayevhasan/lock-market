package uz.pdp.lock_market.payload.feature.req;

import jakarta.persistence.Column;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;

import java.util.List;

public class ReqFeature {
    @Column(nullable = false)
    private Integer memoryOfCards;

    @Column(nullable = false)
    private Boolean application;

    private List<Color> colors;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String battery;

    @Column(nullable = false)
    private UnlockType unlockType;

    @Column(nullable = false)
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
