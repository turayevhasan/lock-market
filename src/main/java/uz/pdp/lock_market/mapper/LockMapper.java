package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;

import java.util.List;
import java.util.UUID;
import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface LockMapper {

    static LockRes entityToDto(Lock lock, List<UUID> photoIds) {
        return LockRes.builder()
                .id(lock.getId())
                .name(lock.getName())
                .description(lock.getDescription())
                .price(lock.getPrice())
                .categoryId(lock.getCategory().getId())
                .photoIds(photoIds)
                .lockType(lock.getLockType())
                .build();
    }

    static void update(Lock lock, LockUpdateReq lockUpdateReq, Category category, List<String> photos) {
        lock.setName(getIfExists(lockUpdateReq.getName(), lock.getName()));
        lock.setDescription(getIfExists(lockUpdateReq.getDescription(), lock.getDescription()));
        lock.setPrice(getIfExists(lockUpdateReq.getPrice(), lock.getPrice()));
        lock.setCategory(getIfExists(category, lock.getCategory()));
        lock.setPhotosPaths(getIfExists(photos, lock.getPhotosPaths()));
        lock.setLockType(getIfExists(lockUpdateReq.getLockType(), lock.getLockType()));
    }
}
