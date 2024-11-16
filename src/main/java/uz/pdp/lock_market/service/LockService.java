package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.entity.base.LockSize;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.FeatureMapper;
import uz.pdp.lock_market.mapper.LockMapper;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.CategoryRepository;
import uz.pdp.lock_market.repository.FeatureRepository;
import uz.pdp.lock_market.repository.LockRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LockService {
    private final LockRepository lockRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    public LockRes add(LockAddReq req) {
        if (lockRepository.existsByName(req.getName())) {
            throw RestException.restThrow(ErrorTypeEnum.LOCK_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        List<String> photos = getPhotoPaths(req.getPhotoIds());

        Lock lock = Lock.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .category(category)
                .photoIds(req.getPhotoIds())
                .lockType(req.getLockType())
                .build();

        lockRepository.save(lock); //saving

        return LockMapper.entityToDto(lock, photos);
    }

    public LockRes update(Long lockId, LockUpdateReq req) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        //updating category
        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

            lock.setCategory(category);
        }

        //updating lock photos
        List<String> photos = Collections.emptyList();
        if (!req.getPhotoIds().isEmpty()) {
            photos = getPhotoPaths(lock.getPhotoIds());
            lock.setPhotoIds(req.getPhotoIds()); //updated
        }

        LockMapper.updateDetails(lock, req);
        lockRepository.save(lock); //saving

        return LockMapper.entityToDto(lock, photos);
    }

    public LockRes get(Long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        List<String> photos = getPhotoPaths(lock.getPhotoIds());

        return LockMapper.entityToDto(lock, photos);
    }

    public List<LockRes> getAllByCategory(long categoryId, int page, int size, Long startPrice, Long endPrice, Color color, LockSize lockSize, String material) {
        if (!categoryRepository.existsById(categoryId)) {
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        return lockRepository.filterLocks(categoryId, startPrice, endPrice, pageable)
                .stream()
                .map(this::entityToRes)
                .toList();
    }

    private LockRes entityToRes(Lock lock) {
        return LockMapper.entityToDto(lock, getPhotoPaths(lock.getPhotoIds()));
    }


    private List<String> getPhotoPaths(List<UUID> lock) {
        List<String> photos = new ArrayList<>();

        for (UUID photoId : lock) {
            Attachment attachment = attachmentRepository.findById(photoId)
                    .orElseThrow(RestException.thew(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

            photos.add(attachment.getFilePath());
        }
        return photos;
    }

}
