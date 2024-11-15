package uz.pdp.lock_market.service;

import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.LockMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.CategoryRepository;
import uz.pdp.lock_market.repository.LockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LockService {
    private final LockRepository lockRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    public LockService(LockRepository lockRepository, CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.lockRepository = lockRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public LockRes add(LockAddReq lockAddReq) {
        Optional<Lock> optionalLock = lockRepository.findByName(lockAddReq.getName());

        if (optionalLock.isPresent()) {
            throw RestException.restThrow(ErrorTypeEnum.LOCK_ALREADY_EXISTS);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(lockAddReq.getCategoryId());

        if (optionalCategory.isEmpty()) {
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NOT_FOUND);
        }

        Category category = optionalCategory.get();

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
                .photosPaths(photos)
                .lockType(lockAddReq.getLockType())
                .build();

        lockRepository.save(lock);

        return LockMapper.entityToDto(lock, lockAddReq.getPhotoIds());
    }

    public LockRes update(Long lockId, LockUpdateReq lockUpdateReq) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));

        Optional<Category> optionalCategory = categoryRepository.findById(lockUpdateReq.getCategoryId());

        if (optionalCategory.isEmpty()) {
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NOT_FOUND);
        }

        Category category = optionalCategory.get();

        List<String> photos = new ArrayList<>();

        for (UUID photoId : lockUpdateReq.getPhotoIds()) {
            Attachment attachment = attachmentRepository.findById(photoId)
                    .orElseThrow(RestException.thew(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

            photos.add(attachment.getFilePath());
        }

        LockMapper.update(lock, lockUpdateReq, category, photos);

        lockRepository.save(lock);

        return LockMapper.entityToDto(lock, lockUpdateReq.getPhotoIds());
    }

    public ResBaseMsg delete(Long lockId) {

        lockRepository.deleteById(lockId);

        return new ResBaseMsg("Lock deleted successfully!");
    }

    public LockRes get(Long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LOCK_NOT_FOUND));


        return null;
//        return LockMapper.entityToDto(lock, )
    }
}
