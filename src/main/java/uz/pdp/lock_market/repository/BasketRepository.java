package uz.pdp.lock_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Basket;

import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

}
