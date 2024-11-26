package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.entity.LockSize;
import uz.pdp.lock_market.enums.Color;

import java.util.Optional;

@Repository
public interface LockRepository extends JpaRepository<Lock, Long>, JpaSpecificationExecutor<Lock> {
    Optional<Lock> findByNameUz(String nameUz);

    Optional<Lock> findByNameRu(String nameRu);

    Optional<Lock> findByNameEn(String nameEn);

    boolean existsByNameUz(String nameUz);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameRu(String nameRu);

    @Query("""
                SELECT l FROM Lock l LEFT JOIN l.feature f
                WHERE (:categoryId IS NULL OR l.category.id = :categoryId)
                  AND (:startPrice IS NULL OR l.newPrice >= :startPrice)
                  AND (:endPrice IS NULL OR l.newPrice <= :endPrice)
                  AND (:color IS NULL OR :color MEMBER OF f.colors)
                  AND (:material IS NULL OR :material = '' OR
                   LOWER(f.materialEn) LIKE LOWER(CONCAT('%', :material, '%')) OR
                   LOWER(f.materialRu) LIKE LOWER(CONCAT('%', :material, '%')) OR
                   LOWER(f.materialUz) LIKE LOWER(CONCAT('%', :material, '%')))
            """)
    Page<Lock> filterLocks(
            @Param("categoryId") Long categoryId,
            @Param("startPrice") Long startPrice,
            @Param("endPrice") Long endPrice,
            @Param("color") Color color,
            @Param("material") String material,
            Pageable pageable
    );


}
