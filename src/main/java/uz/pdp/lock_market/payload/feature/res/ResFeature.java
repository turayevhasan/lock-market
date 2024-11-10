package uz.pdp.lock_market.payload.feature.res;

import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;

import java.util.List;

public class ResFeature {
    private String id;
    private Integer memoryOfCards;
    private Boolean application;
    private List<Color> colors;
    private String material;
    private String battery;
    private UnlockType unlockType;
    private DoorType doorType;
    private String doorWidth;
    private String lockSize;
    private Double weight;
    private String equipment;
    private String createdAt;
    private String updatedAt;
}
