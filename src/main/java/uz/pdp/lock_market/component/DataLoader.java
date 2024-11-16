package uz.pdp.lock_market.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.lock_market.entity.*;
import uz.pdp.lock_market.enums.LockType;
import uz.pdp.lock_market.enums.RoleEnum;
import uz.pdp.lock_market.enums.UserStatus;
import uz.pdp.lock_market.repository.*;

import java.util.List;
import java.util.UUID;

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

    @Override
    public void run(String... args) throws Exception {
        /*
        roleLoad();
        userLoad();
        categoryLoad();
        lockLoad();
        promoCodeLoad();
        appLoad();
         */
    }

    private void roleLoad() {
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
    }

    private void userLoad() {
        User admin = User.builder()
                .email("admin123@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .status(UserStatus.ACTIVE)
                .role(roleRepository.findByName(RoleEnum.ADMIN.name()).orElseThrow())
                .photoPath("E:/PROJECT_DOWNLOAD_FILES/c4ac5743-1237-4c7d-978b-7a6ec4380413.jpg")
                .build();
        userRepository.save(admin);

        User user = User.builder()
                .email("user123@gmail.com")
                .password(passwordEncoder.encode("user123"))
                .status(UserStatus.ACTIVE)
                .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                .photoPath("E:/PROJECT_DOWNLOAD_FILES/2142b4dc-4fbb-442e-8d49-b92d498043b6.jpg")
                .build();
        userRepository.save(user);
    }

    private void categoryLoad() {
        Category category1 = Category.builder()
                .name("Накладные электронные замки")
                .photoId(UUID.fromString("b6838eea-90ae-4e01-97a6-d165dc60aaca"))
                .build();


        Category category2 = Category.builder()
                .name("Врезные электроные замки")
                .photoId(UUID.fromString("2534e0ad-ffcd-47db-baa4-8d803d10ccad"))
                .build();

        Category category3 = Category.builder()
                .name("Замки для квартиры")
                .photoId(UUID.fromString("8c5bd886-939e-4038-a6ee-795327ae2270"))
                .build();


        Category category4 = Category.builder()
                .name("Замки для дома")
                .photoId(UUID.fromString("dd90e6a0-2685-4fad-ad6c-21d266e66c38"))
                .build();


        Category category5 = Category.builder()
                .name("Замки для отелей")
                .photoId(UUID.fromString("220443f5-f026-4a23-963a-35bcf0a9b38a"))
                .build();

        categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5));
    }

    private void lockLoad() {
        Lock lock1 = Lock.builder()
                .name("Дверной Замок Golden Soft для офиса")
                .description("Golden Soft locks is the best")
                .category(categoryRepository.findById(1L).orElseThrow())
                .price(200_000L)
                .photoIds(List.of(UUID.fromString("34921418-3624-46b3-af12-5c54bf389225"), UUID.fromString("3363f987-041f-4fd4-a445-a805566e2c97"), UUID.fromString("66fd4272-c601-4799-be9c-a7dd54668446"), UUID.fromString("2de5ddc4-bdf2-43cf-8fe3-69a347d56214")))
                .lockType(LockType.WITH_APP)
                .build();

        Lock lock2 = Lock.builder()
                .name("Вариативный замок Golden Soft для отеля")
                .description("Golden Soft locks is the best")
                .category(categoryRepository.findById(1L).orElseThrow())
                .price(150_000L)
                .photoIds(List.of(UUID.fromString("a7252ead-1481-4255-99c1-d5b8e19a7478")))
                .lockType(LockType.WITHOUT_APP)
                .build();

        Lock lock3 = Lock.builder()
                .name("Вариативный замок Golden Soft для отеля")
                .description("Golden Soft locks is the best")
                .category(categoryRepository.findById(1L).orElseThrow())
                .price(120_000L)
                .photoIds(List.of(UUID.fromString("7dff54e6-0d32-42fb-8d05-d36b5da47ff4")))
                .lockType(LockType.WITH_APP)
                .build();

        lockRepository.saveAll(List.of(lock1, lock2, lock3));
    }

    private void promoCodeLoad() {
        PromoCode promo1 = new PromoCode("PROMO1", 10_000L, true);
        PromoCode promo2 = new PromoCode("PROMO2", 20_000L, true);
        PromoCode promo3 = new PromoCode("PROMO3", 30_000L, true);
        PromoCode promo4 = new PromoCode("PROMO4", 40_000L, true);
        PromoCode promo5 = new PromoCode("PROMO5", 50_000L, true);

        promoCodeRepository.saveAll(List.of(promo1, promo2, promo3, promo4, promo5));
    }

    private void appLoad() {
        Application app1 = Application.builder()
                .name("TestName1")
                .company("Test Company 1")
                .phone("+998901001010")
                .lock(lockRepository.findById(1L).orElseThrow())
                .lockAmount(10)
                .customLogo(true)
                .helpSetup(true)
                .build();

        Application app2 = Application.builder()
                .name("TestName2")
                .company("Test Company 2")
                .phone("+998902002020")
                .lock(lockRepository.findById(2L).orElseThrow())
                .lockAmount(20)
                .customLogo(false)
                .helpSetup(false)
                .build();

        Application app3 = Application.builder()
                .name("TestName3")
                .company("Test Company 3")
                .phone("+998903003030")
                .lock(lockRepository.findById(3L).orElseThrow())
                .lockAmount(30)
                .customLogo(true)
                .helpSetup(false)
                .build();

        applicationRepository.saveAll(List.of(app1, app2, app3));
    }


}
