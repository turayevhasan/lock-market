package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select a from Application a where (:active is null or a.active = :active)")
    Page<Application> findAllByFilters(
            @Param("active") Boolean active,
            Pageable pageable
    );
}
