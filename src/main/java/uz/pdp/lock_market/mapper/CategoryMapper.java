package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.payload.category.CategoryRes;

public interface CategoryMapper {

    static CategoryRes entityToDto(Category category) {
        String path = category.getPhoto() == null ? "" : category.getPhoto().getFilePath();
        return CategoryRes.builder()
                .id(category.getId())
                .name(category.getName())
                .photoPath(path)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}