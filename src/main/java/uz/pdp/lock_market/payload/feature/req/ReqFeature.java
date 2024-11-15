package uz.pdp.lock_market.payload.feature.req;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;

import java.util.List;
@Getter
@Setter
public class ReqFeature {
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
}
