package uz.pdp.lock_market.payload.feature.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.pdp.lock_market.entity.Battery;
import uz.pdp.lock_market.entity.DoorWidth;
import uz.pdp.lock_market.entity.LockSize;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;
import uz.pdp.lock_market.payload.feature.req.BatteryDto;
import uz.pdp.lock_market.payload.feature.req.DoorWidthDto;
import uz.pdp.lock_market.payload.feature.req.LockSizeDto;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureRes {
    private Long id;
    private Integer memoryOfCards;
    private Boolean application;
    private List<Color> colors;
    private String material;
    private BatteryDto battery;
    private List<UnlockType> unlockTypes;
    private DoorType doorType;
    private DoorWidthDto doorWidth;
    private LockSizeDto lockSize;
    private Double weight;
    private String equipment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
