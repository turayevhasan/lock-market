package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameEn(String nameEn);
    boolean existsByNameUz(String nameUz);
    boolean existsByNameRu(String nameRu);

    @Query("""
            select c from Category c where (:name is null or:name = '' or
            lower(c.nameUz) like lower(concat('%', :name, '%')) or
            lower(c.nameEn) like lower(concat('%', :name, '%')) or
            lower(c.nameRu) like lower(concat('%', :name, '%')))
            """)
    Page<Category> findAllByFilters(
            @Param("name") String name,
            Pageable pageable
    );
}
