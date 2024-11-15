package uz.pdp.lock_market.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lock_market.entity.Attachment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, UUID> {
}
