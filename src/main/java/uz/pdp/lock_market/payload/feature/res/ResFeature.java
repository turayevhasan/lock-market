package uz.pdp.lock_market.payload.feature.res;

import lombok.Builder;
import uz.pdp.lock_market.entity.base.Battery;
import uz.pdp.lock_market.entity.base.DoorWidth;
import uz.pdp.lock_market.entity.base.LockSize;
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
    private Battery battery;
    private UnlockType unlockType;
    private DoorType doorType;
    private DoorWidth doorWidth;
    private LockSize lockSize;
    private Double weight;
    private String equipment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
