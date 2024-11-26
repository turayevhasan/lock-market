package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.payload.user.res.UserRes;

public interface UserMapper {
    static UserRes entityToRes(User user) {
        return UserRes.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .photoPath(user.getPhotoPath())
                .active(user.isActive())
                .deleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
