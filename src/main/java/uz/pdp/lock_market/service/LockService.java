package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.entity.LockSize;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.LockMapper;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.CategoryRepository;
import uz.pdp.lock_market.repository.LockRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LockService {
    private final LockRepository lockRepository;
    private final CategoryRepository categoryRepository;

    public LockRes add(String lang, LockAddReq req) {
        if (lockRepository.existsByNameUz(req.getNameUz())) {
            throw RestException.restThrow(ErrorTypeEnum.LOCK_NAME_UZ_ALREADY_EXISTS);
        }
        if (lockRepository.existsByNameEn(req.getNameEn())) {
            throw RestException.restThrow(ErrorTypeEnum.LOCK_NAME_EN_ALREADY_EXISTS);
        }

        if (lockRepository.existsByNameRu(req.getNameRu())) {
            throw RestException.restThrow(ErrorTypeEnum.LOCK_NAME_RU_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        for (String photo : req.getPhotos()) {
            if (!Files.exists(Path.of(photo)))
                throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
        }

        Lock lock = Lock.builder()
                .nameUz(req.getNameUz())
                .nameRu(req.getNameRu())
                .nameEn(req.getNameEn())
                .descriptionUz(req.getDescriptionUz())
                .descriptionRu(req.getDescriptionRu())
                .descriptionEn(req.getDescriptionEn())
                .price(req.getPrice())
                .newPrice(req.getNewPrice())
                .category(category)
                .photos(req.getPhotos())
                .hasGift(req.getHasGift())
                .lockType(req.getLockType())
                .build();

        lockRepository.save(lock); //saving

        return LockMapper.entityToDto(lock, lang);
    }

    public LockRes update(String lang, Long lockId, LockUpdateReq req) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        //updating category
        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

            lock.setCategory(category);
        }

        //updating lock photos
        if (!req.getPhotos().isEmpty()) {
            for (String photo : req.getPhotos()) {
                if (!Files.exists(Path.of(photo)))
                    throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
            }
            lock.setPhotos(req.getPhotos());
        }

        LockMapper.updateDetails(lock, req);
        lockRepository.save(lock); //saving

        return LockMapper.entityToDto(lock, lang);
    }

    public LockRes get(String lang, Long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        return LockMapper.entityToDto(lock, lang);
    }

    public List<LockRes> getAllByCategory(String lang, Long categoryId, int page, int size, Long startPrice, Long endPrice, Color color, LockSize lockSize, String material) {
        Pageable pageable = PageRequest.of(page, size);

        return lockRepository.filterLocks(categoryId, startPrice, endPrice, color, material, pageable)
                .stream()
                .filter(lock -> filterByLockSize(lock, lockSize))
                .map(lock -> LockMapper.entityToDto(lock, lang))
                .toList();
    }

    private Boolean filterByLockSize(Lock lock, LockSize lockSize) {
        if (lockSize == null)
            return true;

        if (lock.getFeature() == null)
            return true;

        LockSize size = lock.getFeature().getLockSize();
        return Objects.equals(size.getA(), lockSize.getA()) && Objects.equals(size.getB(), lockSize.getB()) && Objects.equals(size.getC(), lockSize.getC());
    }

}
