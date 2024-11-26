package uz.pdp.lock_market.payload.feature.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;

import java.util.List;


@Getter
@Setter
public class FeatureAddReq {
    @NotNull
    private Long lockId;

    @NotNull
    @Min(1)
    private Integer memoryOfCards;

    @NotNull
    private Boolean application;

    @NotEmpty
    private List<Color> colors;

    @NotBlank
    private String materialUz;
    @NotBlank
    private String materialEn;
    @NotBlank
    private String materialRu;

    @NotNull
    private BatteryDto batteryDto;

    @NotNull
    private List<UnlockType> unlockTypes;

    @NotNull
    private DoorType doorType;

    @NotNull
    private DoorWidthDto doorWidthDto;

    @NotNull
    private LockSizeDto lockSizeReq;

    @NotNull
    private Double weight;

    @NotBlank
    private String equipmentUz;
    @NotBlank
    private String equipmentEn;
    @NotBlank
    private String equipmentRu;
}
