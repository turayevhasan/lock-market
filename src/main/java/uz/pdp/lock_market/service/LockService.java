package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.FeatureMapper;
import uz.pdp.lock_market.mapper.LockMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.CategoryRepository;
import uz.pdp.lock_market.repository.FeatureRepository;
import uz.pdp.lock_market.repository.LockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LockService {
    private final LockRepository lockRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final FeatureRepository featureRepository;


    public LockRes add(LockAddReq lockAddReq) {

        if (lockRepository.existsByName(lockAddReq.getName())) {
            throw RestException.restThrow(ErrorTypeEnum.LOCK_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(lockAddReq.getCategoryId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        List<String> photos = new ArrayList<>();

        for (UUID photoId : lockAddReq.getPhotoIds()) {
            Attachment attachment = attachmentRepository.findById(photoId)
                    .orElseThrow(RestException.thew(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

            photos.add(attachment.getFilePath());
        }


        Lock lock = Lock.builder()
                .name(lockAddReq.getName())
                .description(lockAddReq.getDescription())
                .price(lockAddReq.getPrice())
                .category(category)
                .photoIds(lockAddReq.getPhotoIds())
                .lockType(lockAddReq.getLockType())
                .build();

        lockRepository.save(lock);

        return LockMapper.entityToDto(lock, photos);
    }

    public LockRes update(Long lockId, LockUpdateReq lockUpdateReq) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        if (lockUpdateReq.getCategoryId() != null) {
            Category category = categoryRepository.findById(lockUpdateReq.getCategoryId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

            lock.setCategory(category);
        }


        if (!lockUpdateReq.getPhotoIds().isEmpty()) {
            for (UUID photoId : lockUpdateReq.getPhotoIds()) {
                Attachment attachment = attachmentRepository.findById(photoId)
                        .orElseThrow(RestException.thew(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

                lock.getPhotoIds().add(attachment.getId());
            }
        }

        List<String> photos = new ArrayList<>();

        for (UUID photoId : lock.getPhotoIds()) {
            Attachment attachment = attachmentRepository.findById(photoId)
                    .orElseThrow(RestException.thew(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

            photos.add(attachment.getFilePath());
        }

        LockMapper.updateDetails(lock, lockUpdateReq);

        if (lockUpdateReq.getFeatureReq() != null) {
            if (lock.getFeature() != null)
                FeatureMapper.updatedAdd(lock.getFeature(), lockUpdateReq.getFeatureReq());

            else
                lock.setFeature(FeatureMapper.reqToEntity(lockUpdateReq.getFeatureReq()));

            featureRepository.save(lock.getFeature());
        }

        lockRepository.save(lock);

        return LockMapper.entityToDto(lock, photos);
    }

    public ResBaseMsg delete(Long lockId) {

        lockRepository.deleteById(lockId);

        return new ResBaseMsg("Lock deleted successfully!");
    }

    public LockRes get(Long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        List<String> photos = new ArrayList<>();

        for (UUID photoId : lock.getPhotoIds()) {
            Attachment attachment = attachmentRepository.findById(photoId)
                    .orElseThrow(RestException.thew(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

            photos.add(attachment.getFilePath());
        }

        return LockMapper.entityToDto(lock, photos);
    }

    public List<LockRes> getAllByCategory(int page, int size, long id) {
        return null;
    }
}
