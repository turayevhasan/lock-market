package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;

import java.util.List;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface LockMapper {

    static LockRes entityToDto(Lock lock, List<String> photoPaths) {
        return LockRes.builder()
                .id(lock.getId())
                .name(lock.getName())
                .description(lock.getDescription())
                .price(lock.getPrice())
                .categoryId(lock.getCategory().getId())
                .featureRes(lock.getFeature() != null ? FeatureMapper.fromEntityToDto(lock.getFeature()) : null)
                .photoPaths(photoPaths)
                .lockType(lock.getLockType())
                .build();
    }

    static void updateDetails(Lock lock, LockUpdateReq lockUpdateReq) {
        lock.setName(getIfExists(lockUpdateReq.getName(), lock.getName()));
        lock.setDescription(getIfExists(lockUpdateReq.getDescription(), lock.getDescription()));
        lock.setPrice(getIfExists(lockUpdateReq.getPrice(), lock.getPrice()));
        lock.setPhotoIds(getIfExists(lockUpdateReq.getPhotoIds(), lock.getPhotoIds()));
        lock.setLockType(getIfExists(lockUpdateReq.getLockType(), lock.getLockType()));
    }
}
