package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.FeatureMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.FeatureAddReq;
import uz.pdp.lock_market.payload.feature.req.FeatureUpdateReq;
import uz.pdp.lock_market.payload.feature.res.ResFeature;
import uz.pdp.lock_market.repository.FeatureRepository;
import uz.pdp.lock_market.repository.LockRepository;

@Service
@RequiredArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final LockRepository lockRepository;

    public ResFeature add(FeatureAddReq req) {
        Lock lock = lockRepository.findById(req.getLockId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        Feature feature = FeatureMapper.reqToEntity(req);
        feature.setLock(lock);

        featureRepository.save(feature);

        return FeatureMapper.fromEntityToDto(feature);
    }

    public ResFeature update(Long id, FeatureUpdateReq req) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FEATURE_NOT_FOUND));

        if (req.getLockId() != null) {
            Lock lock = lockRepository.findById(req.getLockId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

            feature.setLock(lock); //updated
        }

        FeatureMapper.update(feature, req);
        featureRepository.save(feature); //saved

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

    public ResFeature getByLock(long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        if (lock.getFeature() == null) {
            throw RestException.restThrow(ErrorTypeEnum.FEATURE_NOT_FOUND);
        }
        return FeatureMapper.fromEntityToDto(lock.getFeature());
    }
}
