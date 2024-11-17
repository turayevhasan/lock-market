package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.payload.auth.res.ResUserSimple;

public interface UserMapper {

    static ResUserSimple fromEntityToDto(User user) {
        return ResUserSimple.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .photoPath(user.getPhotoPath())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
