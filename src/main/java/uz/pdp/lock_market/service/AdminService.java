package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.enums.RoleEnum;
import uz.pdp.lock_market.enums.UserStatus;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.repository.RoleRepository;
import uz.pdp.lock_market.repository.UserRepository;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ResourceBundleMessageSource messageSource;

    public ResBaseMsg changeUserRole(String lang, UUID userId, RoleEnum role) {
        User user = userRepository.findById(userId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        user.setRole(
                roleRepository.findByName(role.name())
                        .orElseThrow(RestException.thew(ErrorTypeEnum.ROLE_NOT_FOUND))
        );

        userRepository.save(user);//updated
        return new ResBaseMsg(messageSource.getMessage("change.role", null, Locale.of(lang)));
    }

    public ResBaseMsg changeStatus(String lang, UUID userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        user.setStatus(status);
        userRepository.save(user); //updated

        return new ResBaseMsg(messageSource.getMessage("change.status", null, Locale.of(lang)));
    }
}
