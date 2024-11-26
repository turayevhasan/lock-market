package uz.pdp.lock_market.payload.feature.req;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;

import java.util.List;

@Getter
@Setter
public class FeatureUpdateReq {
    private Long lockId;

    private Integer memoryOfCards;
    private Boolean application;
    private List<Color> colors;
    private String materialUz;
    private String materialEn;
    private String materialRu;
    private BatteryDto batteryDto;
    private List<UnlockType> unlockTypes;
    private DoorType doorType;
    private DoorWidthDto doorWidthDto;
    private LockSizeDto lockSizeReq;
    private Double weight;
    private String equipmentUz;
    private String equipmentEn;
    private String equipmentRu;
}

