package uz.pdp.lock_market.payload.lock.req;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.enums.LockType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LockUpdateReq {
    private String name;

    private String description;

    @Min(1)
    private Long price;

    private Long categoryId;

    private List<UUID> photoIds;

    private LockType lockType;
}
