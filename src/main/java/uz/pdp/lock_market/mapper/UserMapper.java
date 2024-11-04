package uz.pdp.lock_market.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.payload.auth.res.ResUserSimple;

import java.util.ArrayList;
import java.util.List;

public interface UserMapper {

    static ResUserSimple fromEntityToDto(User user) {
        return ResUserSimple.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .profileImagePath(user.getProfileImagePath())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    static List<ResUserSimple> getAllFromPages(Page<User> all) {
        List<ResUserSimple> users = new ArrayList<>();

        for (User user : all) {
            users.add(fromEntityToDto(user));
        }
        return users;
    }
}
