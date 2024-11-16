package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.PromoCode;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    boolean existsByCode(String code);

    Optional<PromoCode> findByCode(String code);

    @Query("""
                SELECT p FROM PromoCode p
                WHERE (:active IS NULL OR p.active = :active)
                AND (:minDiscountPrice IS NULL OR p.discountPrice >= :minDiscountPrice)
                AND (:maxDiscountPrice IS NULL OR p.discountPrice <= :maxDiscountPrice)
            """)
    Page<PromoCode> findAllByFilter(
            @Param("active") Boolean active,
            @Param("minDiscountPrice") Long minDiscountPrice,
            @Param("maxDiscountPrice") Long maxDiscountPrice,
            Pageable pageable);

}
