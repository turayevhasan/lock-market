package uz.pdp.lock_market.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.lock_market.entity.*;
import uz.pdp.lock_market.entity.base.Battery;
import uz.pdp.lock_market.entity.base.DoorWidth;
import uz.pdp.lock_market.entity.base.LockSize;
import uz.pdp.lock_market.enums.*;
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
    private final FeatureRepository featureRepository;

    @Override
    public void run(String... args) throws Exception {
        /*
        roleLoad();
        userLoad();
        categoryLoad();
        lockLoad();
        featureLoad();
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
                .photoPath("D:/PROJECT_DOWNLOAD_FILES/f15d5b11-498b-43bb-9c78-31f7241a7917.jpg")
                .build();
        userRepository.save(admin);

        User user = User.builder()
                .email("user123@gmail.com")
                .password(passwordEncoder.encode("user123"))
                .status(UserStatus.ACTIVE)
                .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                .photoPath("D:/PROJECT_DOWNLOAD_FILES/179b4bcb-050e-496e-8f11-07f956959232.jpg")
                .build();
        userRepository.save(user);
    }

    private void categoryLoad() {
        Category category1 = Category.builder()
                .name("Накладные электронные замки")
                .photoId(UUID.fromString("d5f24c96-5d11-4485-8e92-d3a344e6b7ac"))
                .build();


        Category category2 = Category.builder()
                .name("Врезные электроные замки")
                .photoId(UUID.fromString("4f9af74f-17f4-4226-873d-d00f83d938e8"))
                .build();

        Category category3 = Category.builder()
                .name("Замки для квартиры")
                .photoId(UUID.fromString("59053b9b-aeb2-4f44-8914-cda1e3296a64"))
                .build();


        Category category4 = Category.builder()
                .name("Замки для дома")
                .photoId(UUID.fromString("70695eaa-a257-44ad-9478-e05d29e5c2a3"))
                .build();


        Category category5 = Category.builder()
                .name("Замки для отелей")
                .photoId(UUID.fromString("169fca81-58a4-42f8-b65e-9fdd8d05b5f1"))
                .build();

        categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5));
    }

    private void lockLoad() {
        Lock lock1 = Lock.builder()
                .name("Дверной Замок Golden Soft для офиса")
                .description("Golden Soft locks is the best")
                .category(categoryRepository.findById(1L).orElseThrow())
                .price(200_000L)
                .photoIds(List.of(UUID.fromString("af62de0e-125d-4290-bcbd-f17d27097e40"), UUID.fromString("b409ee55-3f68-4524-b41b-4fd5b5f37ac9"), UUID.fromString("e6473697-14f9-4280-8251-bf753bd5ee48"), UUID.fromString("5d325d21-b4f5-40a3-9cb9-db45cf2e5d78")))
                .lockType(LockType.WITH_APP)
                .build();

        Lock lock2 = Lock.builder()
                .name("Вариативный замок Golden Soft для отеля")
                .description("Golden Soft locks is the best")
                .category(categoryRepository.findById(1L).orElseThrow())
                .price(150_000L)
                .photoIds(List.of(UUID.fromString("fc45b644-0ed9-42e6-952b-40320759ef79")))
                .lockType(LockType.WITHOUT_APP)
                .build();

        Lock lock3 = Lock.builder()
                .name("Вариативный замок Golden Soft для отеля")
                .description("Golden Soft locks is the best")
                .category(categoryRepository.findById(1L).orElseThrow())
                .price(120_000L)
                .photoIds(List.of(UUID.fromString("cdb7ad5f-e56f-4d13-a6e2-d2d2d7d6daca")))
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

    private void featureLoad(){
        Feature feature1 = Feature.builder()
                .lock(lockRepository.findById(1L).orElseThrow())
                .memoryOfCards(100)
                .application(true)
                .colors(List.of(Color.CHROME, Color.BLACK))
                .material("Material1")
                .battery(new Battery(4, 5))
                .unlockType(UnlockType.PIN_CODE)
                .doorType(DoorType.WOODEN)
                .doorWidth(new DoorWidth(35.8, 20.6))
                .lockSize(new LockSize(10, 20, 30))
                .weight(10.0)
                .equipment("Equipment1")
                .build();

        Feature feature2 = Feature.builder()
                .lock(lockRepository.findById(2L).orElseThrow())
                .memoryOfCards(120)
                .application(true)
                .colors(List.of(Color.CHROME, Color.DEFAULT))
                .material("Material2")
                .battery(new Battery(3, 6))
                .unlockType(UnlockType.KEY)
                .doorType(DoorType.METAL)
                .doorWidth(new DoorWidth(34.6, 22.4))
                .lockSize(new LockSize(12, 22, 33))
                .weight(12.0)
                .equipment("Equipment2")
                .build();

        Feature feature3 = Feature.builder()
                .lock(lockRepository.findById(3L).orElseThrow())
                .memoryOfCards(110)
                .application(true)
                .colors(List.of(Color.BLACK, Color.DEFAULT))
                .material("Material3")
                .battery(new Battery(4, 4))
                .unlockType(UnlockType.BRACELET)
                .doorType(DoorType.WOODEN)
                .doorWidth(new DoorWidth(32.6, 26.6))
                .lockSize(new LockSize(14, 20, 32))
                .weight(14.0)
                .equipment("Equipment3")
                .build();

        featureRepository.saveAll(List.of(feature1, feature2, feature3));
    }


}
