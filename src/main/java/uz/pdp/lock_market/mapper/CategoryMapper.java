package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.payload.category.res.CategoryRes;

public interface CategoryMapper {

    static CategoryRes entityToDto(Category category, String lang) {
        return switch (lang) {
            case "uz" -> CategoryRes.builder()
                    .id(category.getId())
                    .name(category.getNameUz())
                    .photoPath(category.getPhotoPath())
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();

            case "ru" -> CategoryRes.builder()
                    .id(category.getId())
                    .name(category.getNameRu())
                    .photoPath(category.getPhotoPath())
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();

            default -> CategoryRes.builder()
                    .id(category.getId())
                    .name(category.getNameEn())
                    .photoPath(category.getPhotoPath())
                    .createdAt(category.getCreatedAt())
                    .updatedAt(category.getUpdatedAt())
                    .build();
        };
    }
}
