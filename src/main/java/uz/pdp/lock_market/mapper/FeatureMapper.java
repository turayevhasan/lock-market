package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.payload.feature.req.FeatureUpdateReq;
import uz.pdp.lock_market.payload.feature.res.FeatureRes;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface FeatureMapper {
    static FeatureRes fromEntityToDto(Feature feature, String lang) {
        return switch (lang) {
            case "uz" -> FeatureRes.builder()
                    .id(feature.getId())
                    .memoryOfCards(feature.getMemoryOfCards())
                    .application(feature.getApplication())
                    .colors(feature.getColors())
                    .material(feature.getMaterialUz())
                    .battery(feature.getBattery().getDto())
                    .unlockTypes(feature.getUnlockTypes())
                    .doorType(feature.getDoorType())
                    .doorWidth(feature.getDoorWidth().getDto())
                    .lockSize(feature.getLockSize().getDto())
                    .weight(feature.getWeight())
                    .equipment(feature.getEquipmentUz())
                    .updatedAt(feature.getUpdatedAt())
                    .createdAt(feature.getCreatedAt())
                    .build();
            case "ru" -> FeatureRes.builder()
                    .id(feature.getId())
                    .memoryOfCards(feature.getMemoryOfCards())
                    .application(feature.getApplication())
                    .colors(feature.getColors())
                    .material(feature.getMaterialRu())
                    .battery(feature.getBattery().getDto())
                    .unlockTypes(feature.getUnlockTypes())
                    .doorType(feature.getDoorType())
                    .doorWidth(feature.getDoorWidth().getDto())
                    .lockSize(feature.getLockSize().getDto())
                    .weight(feature.getWeight())
                    .equipment(feature.getEquipmentRu())
                    .updatedAt(feature.getUpdatedAt())
                    .createdAt(feature.getCreatedAt())
                    .build();

            default -> FeatureRes.builder()
                    .id(feature.getId())
                    .memoryOfCards(feature.getMemoryOfCards())
                    .application(feature.getApplication())
                    .colors(feature.getColors())
                    .material(feature.getMaterialEn())
                    .battery(feature.getBattery().getDto())
                    .unlockTypes(feature.getUnlockTypes())
                    .doorType(feature.getDoorType())
                    .doorWidth(feature.getDoorWidth().getDto())
                    .lockSize(feature.getLockSize().getDto())
                    .weight(feature.getWeight())
                    .equipment(feature.getEquipmentEn())
                    .updatedAt(feature.getUpdatedAt())
                    .createdAt(feature.getCreatedAt())
                    .build();
        };
    }

    static void updateDetails(Feature feature, FeatureUpdateReq req) {
        feature.setMaterialUz(getIfExists(req.getMaterialUz(), feature.getMaterialUz()));
        feature.setMaterialEn(getIfExists(req.getMaterialEn(), feature.getMaterialEn()));
        feature.setMaterialRu(getIfExists(req.getMaterialRu(), feature.getMaterialRu()));

        feature.setEquipmentUz(getIfExists(req.getEquipmentUz(), feature.getEquipmentUz()));
        feature.setEquipmentEn(getIfExists(req.getEquipmentEn(), feature.getEquipmentEn()));
        feature.setEquipmentRu(getIfExists(req.getEquipmentRu(), feature.getEquipmentRu()));

        feature.setMemoryOfCards(getIfExists(req.getMemoryOfCards(), feature.getMemoryOfCards()));
        feature.setApplication(getIfExists(req.getApplication(), feature.getApplication()));
        feature.setColors(getIfExists(req.getColors(), feature.getColors()));
        feature.setUnlockTypes(getIfExists(req.getUnlockTypes(), feature.getUnlockTypes()));
        feature.setDoorType(getIfExists(req.getDoorType(), feature.getDoorType()));
        feature.setWeight(getIfExists(req.getWeight(), feature.getWeight()));
    }
}
