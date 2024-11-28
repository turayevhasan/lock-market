package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.payload.category.req.CategoryUpdateReq;
import uz.pdp.lock_market.payload.category.req.CategoryAddReq;
import uz.pdp.lock_market.mapper.CategoryMapper;
import uz.pdp.lock_market.payload.category.res.CategoryFullRes;
import uz.pdp.lock_market.payload.category.res.CategoryRes;
import uz.pdp.lock_market.repository.CategoryRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryFullRes add(CategoryAddReq req) {
        if (categoryRepository.existsByNameUz(req.getNameUz()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NAME_UZ_ALREADY_EXISTS);

        if (categoryRepository.existsByNameEn(req.getNameEn()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NAME_EN_ALREADY_EXISTS);

        if (categoryRepository.existsByNameRu(req.getNameRu()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NAME_RU_ALREADY_EXISTS);

        if (!Files.exists(Path.of(req.getPhotoPath()))) {
            throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
        }

        Category category = Category.builder()
                .nameUz(req.getNameUz())
                .nameEn(req.getNameEn())
                .nameRu(req.getNameRu())
                .photoPath(req.getPhotoPath())
                .build();

        categoryRepository.save(category);  //saving

        return CategoryMapper.entityToFullDto(category);
    }

    public CategoryFullRes update(long id, CategoryUpdateReq req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        if (!category.getNameUz().equals(req.getNameUz()) && categoryRepository.existsByNameUz(req.getNameUz()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NAME_UZ_ALREADY_EXISTS);

        if (!category.getNameEn().equals(req.getNameEn()) && categoryRepository.existsByNameEn(req.getNameEn()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NAME_EN_ALREADY_EXISTS);

        if (!category.getNameRu().equals(req.getNameRu()) && categoryRepository.existsByNameRu(req.getNameRu()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_NAME_RU_ALREADY_EXISTS);

        category.setNameUz(getIfExists(req.getNameUz(), category.getNameUz()));
        category.setNameEn(getIfExists(req.getNameEn(), category.getNameEn()));
        category.setNameRu(getIfExists(req.getNameRu(), category.getNameRu()));

        if (req.getPhotoPath() != null) {
            if (!Files.exists(Path.of(req.getPhotoPath()))) {
                throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
            }
            category.setPhotoPath(req.getPhotoPath());
        }
        categoryRepository.save(category); //updating

        return CategoryMapper.entityToFullDto(category);
    }

    public CategoryRes getOne(String lang, long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        return CategoryMapper.entityToDto(category, lang);
    }

    public List<CategoryRes> getAll(String lang, int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);

        return categoryRepository.findAllByFilters(name, pageable)
                .stream()
                .map(category -> CategoryMapper.entityToDto(category, lang))
                .toList();
    }
}
