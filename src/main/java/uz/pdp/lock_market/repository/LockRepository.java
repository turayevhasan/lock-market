package uz.pdp.lock_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Lock;

import java.util.Optional;

@Repository
public interface LockRepository extends JpaRepository<Lock, Long> {
    Optional<Lock> findByName(String name);
    boolean existsByName(String name);
}
