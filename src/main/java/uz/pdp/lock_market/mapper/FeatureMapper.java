package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;
import uz.pdp.lock_market.payload.feature.res.ResFeature;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

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

    static Feature reqToEntity(ReqFeature feature) {
        return Feature.builder()
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
