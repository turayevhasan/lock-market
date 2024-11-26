package uz.pdp.lock_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Battery;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
}
