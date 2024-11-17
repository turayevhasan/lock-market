package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.FeatureMapper;
import uz.pdp.lock_market.mapper.LockMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;
import uz.pdp.lock_market.payload.feature.res.ResFeature;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.repository.FeatureRepository;
import uz.pdp.lock_market.repository.LockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final LockRepository lockRepository;

    public ResFeature add(ReqFeature reqFeature) {

        if (lockRepository.existsById(reqFeature.getLockId())){
            throw RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND);
        }

        Feature feature = FeatureMapper.reqToEntity(reqFeature);

        featureRepository.save(feature);

        return FeatureMapper.fromEntityToDto(feature);
    }

    public ResFeature update(Long featureId, ReqFeature reqFeature) {

        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FEATURE_NOT_FOUND));

        if (lockRepository.existsById(reqFeature.getLockId())){
            throw RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND);
        }

        FeatureMapper.updatedAdd(feature,reqFeature);
        featureRepository.save(feature);

        return FeatureMapper.fromEntityToDto(feature);
    }

    public ResBaseMsg delete(Long featureId) {

        lockRepository.deleteById(featureId);

        return new ResBaseMsg("Feature deleted successfully!");
    }


    public ResFeature get(Long id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FEATURE_NOT_FOUND));

        return FeatureMapper.fromEntityToDto(feature);
    }
}
