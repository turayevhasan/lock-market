package uz.pdp.lock_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where (:read is null or :read = c.read)")
    List<Contact> findAllByFilter(
            @Param("read") Boolean read,
            Pageable pageable
    );
}
