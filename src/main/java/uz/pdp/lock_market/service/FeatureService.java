package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.*;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.FeatureMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.FeatureAddReq;
import uz.pdp.lock_market.payload.feature.req.FeatureUpdateReq;
import uz.pdp.lock_market.payload.feature.res.FeatureRes;
import uz.pdp.lock_market.repository.*;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final LockRepository lockRepository;
    private final DoorWidthRepository doorWidthRepository;
    private final LockSizeRepository lockSizeRepository;
    private final BatteryRepository batteryRepository;
    private final MessageSource messageSource;

    public FeatureRes add(String lang, FeatureAddReq req) {
        Lock lock = lockRepository.findById(req.getLockId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        DoorWidth doorWidth = doorWidthRepository.save(new DoorWidth(req.getDoorWidthDto()));
        LockSize lockSize = lockSizeRepository.save(new LockSize(req.getLockSizeReq()));
        Battery battery = batteryRepository.save(new Battery(req.getBatteryDto()));

        Feature feature = Feature.builder()
                .lock(lock)
                .memoryOfCards(req.getMemoryOfCards())
                .application(req.getApplication())
                .battery(battery)
                .colors(req.getColors())
                .materialUz(req.getMaterialUz())
                .materialEn(req.getMaterialEn())
                .materialRu(req.getMaterialRu())
                .unlockTypes(req.getUnlockTypes())
                .doorType(req.getDoorType())
                .doorWidth(doorWidth)
                .lockSize(lockSize)
                .weight(req.getWeight())
                .equipmentUz(req.getEquipmentUz())
                .equipmentEn(req.getEquipmentEn())
                .equipmentRu(req.getEquipmentRu())
                .build();

        featureRepository.save(feature);  //saved
        return FeatureMapper.fromEntityToDto(feature, lang);
    }

    public FeatureRes update(String lang, Long id, FeatureUpdateReq req) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FEATURE_NOT_FOUND));

        if (req.getLockId() != null) {
            Lock lock = lockRepository.findById(req.getLockId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

            feature.setLock(lock); //updated
        }

        FeatureMapper.updateDetails(feature, req); //updating

        Battery battery = feature.getBattery();
        if (req.getBatteryDto() != null) {
            battery.setAmpere(req.getBatteryDto().getAmpere());
            battery.setVoltage(req.getBatteryDto().getVoltage());
            batteryRepository.save(battery); //updating;
        }
        DoorWidth doorWidth = feature.getDoorWidth();
        if (req.getDoorWidthDto() != null) {
            doorWidth.setA(req.getDoorWidthDto().getA());
            doorWidth.setB(req.getDoorWidthDto().getB());
            doorWidthRepository.save(doorWidth);//updating;
        }

        featureRepository.save(feature); //saved

        return FeatureMapper.fromEntityToDto(feature, lang);
    }

    public ResBaseMsg delete(String lang, Long featureId) {
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FEATURE_NOT_FOUND));

        featureRepository.delete(feature);

        return new ResBaseMsg(messageSource.getMessage("feature.deleted", null, Locale.of(lang)));
    }


    public FeatureRes get(String lang, Long id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FEATURE_NOT_FOUND));

        return FeatureMapper.fromEntityToDto(feature, lang);
    }

    public FeatureRes getByLock(String lang, long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        if (lock.getFeature() == null) {
            throw RestException.restThrow(ErrorTypeEnum.FEATURE_NOT_FOUND);
        }
        return FeatureMapper.fromEntityToDto(lock.getFeature(), lang);
    }
}
