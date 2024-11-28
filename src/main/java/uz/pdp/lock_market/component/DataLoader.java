package uz.pdp.lock_market.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.lock_market.entity.Role;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.enums.RoleEnum;
import uz.pdp.lock_market.enums.UserStatus;
import uz.pdp.lock_market.repository.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final ApplicationRepository applicationRepository;
    private final LockRepository lockRepository;
    private final FeatureRepository featureRepository;
    private final BatteryRepository batteryRepository;
    private final LockSizeRepository lockSizeRepository;
    private final DoorWidthRepository doorWidthRepository;

    @Override
    public void run(String... args) throws Exception {
//        roleLoad();
//        adminLoad();
    }


    private void roleLoad() {
        roleRepository.save(new Role(RoleEnum.ADMIN.name()));
        roleRepository.save(new Role(RoleEnum.USER.name()));
        roleRepository.save(new Role(RoleEnum.MANAGER.name()));
    }

    private void adminLoad() {
        User user = User.builder()
                .name("Admin")
                .email("admin")
                .password(passwordEncoder.encode("admin123"))
                .status(UserStatus.ACTIVE)
                .role(roleRepository.findByName(RoleEnum.ADMIN.name()).orElseThrow())
                .photoPath(null)
                .build();
        userRepository.save(user);
    }
}
