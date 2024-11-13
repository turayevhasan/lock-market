package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Category;
import uz.pdp.lock_market.entity.PromoCode;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Optional<Category> findByName(String name);

    @Query("select c from Category c where (:name is null or lower(c.name) like lower(concat('%', :name, '%'))) ")
    Page<Category> findAllByFilters(
            @Param("code") String name,
            Pageable pageable
    );
}
