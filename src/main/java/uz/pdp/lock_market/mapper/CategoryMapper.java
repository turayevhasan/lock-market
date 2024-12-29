package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.payload.category.res.CategoryFullRes;
import uz.pdp.lock_market.payload.category.res.CategoryRes;
import uz.pdp.lock_market.util.BaseConstants;

public interface CategoryMapper {

    static CategoryRes entityToDto(Category category, String lang) {
        String photoPath = category.getPhotoPath() == null ? "" : BaseConstants.SERVER_HOST + category.getPhotoPath();
        return switch (lang) {
            case "uz" -> CategoryRes.builder()
                    .id(category.getId())
                    .name(category.getNameUz())
                    .photoPath(photoPath)
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();

            case "ru" -> CategoryRes.builder()
                    .id(category.getId())
                    .name(category.getNameRu())
                    .photoPath(photoPath)
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();

            default -> CategoryRes.builder()
                    .id(category.getId())
                    .name(category.getNameEn())
                    .photoPath(photoPath)
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();
        };
    }

    static CategoryFullRes entityToFullDto(Category category) {
        String photoPath = category.getPhotoPath() == null ? "" : BaseConstants.SERVER_HOST + category.getPhotoPath();
        return CategoryFullRes.builder()
                .id(category.getId())
                .nameUz(category.getNameUz())
                .nameEn(category.getNameEn())
                .nameRu(category.getNameRu())
                .photoPath(photoPath)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
