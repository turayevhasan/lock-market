package uz.pdp.lock_market.payload.feature.res;

import lombok.Builder;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public class ResFeature {
    private Long id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
