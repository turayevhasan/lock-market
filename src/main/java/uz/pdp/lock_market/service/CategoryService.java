package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.payload.category.CategoryUpdateReq;
import uz.pdp.lock_market.payload.category.CategoryAddReq;
import uz.pdp.lock_market.mapper.CategoryMapper;
import uz.pdp.lock_market.payload.category.CategoryRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.CategoryRepository;

import java.util.List;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    public CategoryRes add(CategoryAddReq req) {
        if (categoryRepository.existsByName(req.getName()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_ALREADY_EXISTS);

        Attachment attachment = null;

        if (req.getPhotoId() != null) {
            attachment = attachmentRepository.findById(req.getPhotoId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND));

        }
        String photoPath = attachment == null ? "" : attachment.getFilePath();
        Category category = new Category(req.getName(), photoPath);
        categoryRepository.save(category);  //saving

        return CategoryMapper.entityToDto(category);
    }


    public CategoryRes update(long id, CategoryUpdateReq req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        if (!category.getName().equals(req.getName()) && categoryRepository.existsByName(req.getName()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_ALREADY_EXISTS);

        category.setName(getIfExists(req.getName(), category.getName()));

        if (req.getPhotoId() != null) {
            Attachment photo = attachmentRepository.findById(req.getPhotoId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND));
            category.setPhotoPath(photo.getFilePath());
        }
        categoryRepository.save(category); //updating

        return CategoryMapper.entityToDto(category);
    }

    public CategoryRes getOne(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        return CategoryMapper.entityToDto(category);
    }

    public List<CategoryRes> getAll(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        return categoryRepository.findAllByFilters(name, pageable)
                .stream()
                .map(CategoryMapper::entityToDto)
                .toList();
    }
}
