package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.entity.base.LockSize;
import uz.pdp.lock_market.enums.Color;

import java.util.Optional;

@Repository
public interface LockRepository extends JpaRepository<Lock, Long> {
    Optional<Lock> findByName(String name);

    boolean existsByName(String name);

    @Query("""
            SELECT l FROM Lock l
            WHERE l.category.id = :categoryId
            AND l.price BETWEEN :startPrice AND :endPrice
            AND (:color is null or :color in (l.feature.colors))
            AND (:material is null or lower(l.feature.material) like lower(concat('%', :material, '%')))
            AND (:lockSize is null or l.feature.lockSize = :lockSize)
            """)
    Page<Lock> filterLocks(
            @Param("categoryId") Long categoryId,
            @Param("startPrice") Long startPrice,
            @Param("endPrice") Long endPrice,
            @Param("material") String material,
            @Param("color") Color color,
            @Param("lockSize") LockSize lockSize,
            Pageable pageable
    );

}
