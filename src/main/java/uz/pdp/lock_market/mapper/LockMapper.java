package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;

import java.util.List;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface LockMapper {

    static LockRes entityToDto(Lock lock, String lang) {
        return switch (lang) {
            case "uz" -> LockRes.builder()
                    .id(lock.getId())
                    .name(lock.getNameUz())
                    .description(lock.getDescriptionUz())
                    .price(lock.getPrice())
                    .newPrice(lock.getNewPrice())
                    .categoryId(lock.getCategory().getId())
                    .photos(lock.getPhotos())
                    .lockType(lock.getLockType().getKey())
                    .hasGift(lock.getHasGift())
                    .createdAt(lock.getCreatedAt())
                    .updatedAt(lock.getUpdatedAt())
                    .build();

            case "ru" -> LockRes.builder()
                    .id(lock.getId())
                    .name(lock.getNameRu())
                    .description(lock.getDescriptionRu())
                    .price(lock.getPrice())
                    .newPrice(lock.getNewPrice())
                    .categoryId(lock.getCategory().getId())
                    .photos(lock.getPhotos())
                    .lockType(lock.getLockType().getKey())
                    .hasGift(lock.getHasGift())
                    .createdAt(lock.getCreatedAt())
                    .updatedAt(lock.getUpdatedAt())
                    .build();

            default -> LockRes.builder()
                    .id(lock.getId())
                    .name(lock.getNameEn())
                    .description(lock.getDescriptionEn())
                    .price(lock.getPrice())
                    .newPrice(lock.getNewPrice())
                    .categoryId(lock.getCategory().getId())
                    .photos(lock.getPhotos())
                    .lockType(lock.getLockType().getKey())
                    .hasGift(lock.getHasGift())
                    .createdAt(lock.getCreatedAt())
                    .updatedAt(lock.getUpdatedAt())
                    .build();
        };
    }

    static void updateDetails(Lock lock, LockUpdateReq req) {
        lock.setNameUz(getIfExists(req.getNameUz(), lock.getNameUz()));
        lock.setNameRu(getIfExists(req.getNameRu(), lock.getNameRu()));
        lock.setNameEn(getIfExists(req.getNameEn(), lock.getNameEn()));

        lock.setDescriptionUz(getIfExists(req.getDescriptionUz(), lock.getDescriptionUz()));
        lock.setDescriptionRu(getIfExists(req.getDescriptionRu(), lock.getDescriptionRu()));
        lock.setDescriptionEn(getIfExists(req.getDescriptionEn(), lock.getDescriptionEn()));

        lock.setPrice(getIfExists(req.getPrice(), lock.getPrice()));
        lock.setNewPrice(getIfExists(req.getNewPrice(), lock.getNewPrice()));
        lock.setLockType(getIfExists(req.getLockType(), lock.getLockType()));
        lock.setHasGift(getIfExists(req.getHasGift(), lock.getHasGift()));
    }
}
