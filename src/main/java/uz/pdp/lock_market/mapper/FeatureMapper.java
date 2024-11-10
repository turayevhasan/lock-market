package uz.pdp.lock_market.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.payload.auth.res.ResUserSimple;
import uz.pdp.lock_market.payload.feature.res.ResFeature;

import java.util.ArrayList;
import java.util.List;

public interface FeatureMapper {
//    static ResUserSimple fromEntityToDto(User user) {
//        return ResUserSimple.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .role(user.getRole().getName())
//                .profileImagePath(user.getProfileImagePath())
//                .createdAt(user.getCreatedAt())
//                .updatedAt(user.getUpdatedAt())
//                .build();
//    }
//    static List<ResUserSimple> getAllFromPages(Page<ResFeature.>000 all) {
//        List<ResUserSimple> users = new ArrayList<>();
//
//        for (User user : all) {
//            users.add(fromEntityToDto(user));
//        }
//        return users;
//    }
}
