package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.payload.user.res.UserRes;
import uz.pdp.lock_market.util.BaseConstants;

public interface UserMapper {
    static UserRes entityToRes(User user) {
        String photoPath = user.getPhotoPath() == null ? "" : BaseConstants.SERVER_HOST + user.getPhotoPath();
        return UserRes.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .photoPath(photoPath)
                .active(user.isActive())
                .deleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
