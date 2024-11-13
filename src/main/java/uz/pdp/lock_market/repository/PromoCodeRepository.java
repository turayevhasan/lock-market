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

    @Query("select p from PromoCode p " +
            "where (:code is null or lower(p.code) like lower(concat('%', :code, '%'))) " +
            "and (:discountPriceLessThan is null or p.discountPrice < :discountPriceLessThan) " +
            "and (:discountPriceMoreThan is null or p.discountPrice > :discountPriceMoreThan) " +
            "and (:active is null or p.active = :active)")
    Page<PromoCode> findAllByFilters(
            @Param("code") String code,
            @Param("discountPriceLessThan") Long discountPriceLessThan,
            @Param("discountPriceMoreThan") Long discountPriceMoreThan,
            @Param("active") Boolean active,
            Pageable pageable
    );
}
