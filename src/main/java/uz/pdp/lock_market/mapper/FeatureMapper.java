package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.payload.feature.req.FeatureAddReq;
import uz.pdp.lock_market.payload.feature.req.FeatureUpdateReq;
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

    static Feature reqToEntity(FeatureAddReq feature) {
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


    static void update(Feature feature, FeatureUpdateReq req) {
        feature.setMemoryOfCards(getIfExists(req.getMemoryOfCards(), feature.getMemoryOfCards()));
        feature.setApplication(getIfExists(req.getApplication(), feature.getApplication()));
        feature.setColors(getIfExists(req.getColors(), feature.getColors()));
        feature.setMaterial(getIfExists(req.getMaterial(), feature.getMaterial()));
        feature.setBattery(getIfExists(req.getBattery(), feature.getBattery()));
        feature.setUnlockType(getIfExists(req.getUnlockType(), feature.getUnlockType()));
        feature.setDoorType(getIfExists(req.getDoorType(), feature.getDoorType()));
        feature.setLockSize(getIfExists(req.getLockSize(), feature.getLockSize()));
        feature.setWeight(getIfExists(req.getWeight(), feature.getWeight()));
        feature.setEquipment(getIfExists(req.getEquipment(), feature.getEquipment()));
    }
}
