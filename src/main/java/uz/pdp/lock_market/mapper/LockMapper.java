package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockFullRes;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.util.BaseConstants;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface LockMapper {

    static LockRes entityToDto(Lock lock, String lang) {
        List<String> photos = new ArrayList<>();
        for (String photo : lock.getPhotos()) {
            if (photo != null && !photo.isEmpty())
                photos.add(BaseConstants.SERVER_HOST + photo);
        }
        return switch (lang) {
            case "uz" -> LockRes.builder()
                    .id(lock.getId())
                    .name(lock.getNameUz())
                    .description(lock.getDescriptionUz())
                    .price(lock.getPrice())
                    .newPrice(lock.getNewPrice())
                    .categoryId(lock.getCategory().getId())
                    .photos(photos)
                    .lockType(lock.getLockType().getKey())
                    .hasGift(lock.getHasGift())
                    .deleted(lock.isDeleted())
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
                    .photos(photos)
                    .lockType(lock.getLockType().getKey())
                    .hasGift(lock.getHasGift())
                    .deleted(lock.isDeleted())
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
                    .photos(photos)
                    .lockType(lock.getLockType().getKey())
                    .hasGift(lock.getHasGift())
                    .deleted(lock.isDeleted())
                    .createdAt(lock.getCreatedAt())
                    .updatedAt(lock.getUpdatedAt())
                    .build();
        };
    }

    static LockFullRes entityToFullRes(Lock lock) {
        List<String> photos = new ArrayList<>();
        for (String photo : lock.getPhotos()) {
            if (photo != null && !photo.isEmpty())
                photos.add(BaseConstants.SERVER_HOST + photo);
        }
        return LockFullRes.builder()
                .id(lock.getId())
                .nameUz(lock.getNameUz())
                .nameEn(lock.getNameEn())
                .nameRu(lock.getNameRu())
                .descriptionUz(lock.getDescriptionUz())
                .descriptionEn(lock.getDescriptionEn())
                .descriptionRu(lock.getDescriptionRu())
                .price(lock.getPrice())
                .newPrice(lock.getNewPrice())
                .categoryId(lock.getCategory().getId())
                .photos(photos)
                .lockType(lock.getLockType().getKey())
                .hasGift(lock.getHasGift())
                .deleted(lock.isDeleted())
                .createdAt(lock.getCreatedAt())
                .updatedAt(lock.getUpdatedAt())
                .build();
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
