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
                .photoPaths(photoPaths)
                .lockType(lock.getLockType())
                .createdAt(lock.getCreatedAt())
                .updatedAt(lock.getUpdatedAt())
                .build();
    }

    static void updateDetails(Lock lock, LockUpdateReq req) {
        lock.setName(getIfExists(req.getName(), lock.getName()));
        lock.setDescription(getIfExists(req.getDescription(), lock.getDescription()));
        lock.setPrice(getIfExists(req.getPrice(), lock.getPrice()));
        lock.setLockType(getIfExists(req.getLockType(), lock.getLockType()));
    }
}
