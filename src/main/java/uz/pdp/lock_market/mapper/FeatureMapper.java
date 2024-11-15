package uz.pdp.lock_market.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.DoorType;
import uz.pdp.lock_market.enums.UnlockType;
import uz.pdp.lock_market.payload.auth.res.ResUserSimple;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;
import uz.pdp.lock_market.payload.feature.res.ResFeature;

import java.util.ArrayList;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;
//import java.util.List;
//private Integer memoryOfCards;
//
//private Boolean application;
//
//private List<Color> colors;
//
//private String material;
//
//private String battery;
//
//private UnlockType unlockType;
//
//private DoorType doorType;
//
//private String doorWidth;
//
//private String lockSize;
//
//private Double weight;
//
//private String equipment;

public interface FeatureMapper {
    static ResFeature fromEntityToDto(Feature feature) {
        return ResFeature.builder()
                .id(feature.getId())
                .memoryOfCards(feature.getMemoryOfCards())
                .application(feature.getApplication())
                .colors(feature.getColors())
                .material(feature.getMaterial())
                .battery(feature.getBattery())
                .unlockType(feature.getUnlockType())
                .doorType(feature.getDoorType())
                .doorWidth(feature.getDoorWidth())
                .lockSize(feature.getLockSize())
                .weight(feature.getWeight())
                .equipment(feature.getEquipment())
                .updatedAt(feature.getUpdatedAt())
                .createdAt(feature.getCreatedAt())
                .build();
    }


    static void updatedAdd(Feature feature, ReqFeature reqFeature) {
        feature.setMemoryOfCards(getIfExists(reqFeature.getMemoryOfCards(),feature.getMemoryOfCards()));
        feature.setApplication(getIfExists(reqFeature.getApplication(),feature.getApplication()));
        feature.setColors(getIfExists(reqFeature.getColors(),feature.getColors()));
        feature.setMaterial(getIfExists(reqFeature.getMaterial(),feature.getMaterial()));
        feature.setBattery(getIfExists(reqFeature.getBattery(),feature.getBattery()));
        feature.setUnlockType(getIfExists(reqFeature.getUnlockType(),feature.getUnlockType()));
        feature.setDoorType(getIfExists(reqFeature.getDoorType(),feature.getDoorType()));
        feature.setLockSize(getIfExists(reqFeature.getLockSize(),feature.getLockSize()));
        feature.setWeight(getIfExists(reqFeature.getWeight(),feature.getWeight()));
        feature.setEquipment(getIfExists(reqFeature.getEquipment(),feature.getEquipment()));

    }
}
