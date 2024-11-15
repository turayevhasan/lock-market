package uz.pdp.lock_market.payload.lock.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.entity.Feature;
import uz.pdp.lock_market.enums.LockType;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LockUpdateReq {

    private String name;

    private String description;

    private Long price;

    private Long categoryId;

    private List<UUID> photoIds;

    private LockType lockType;

    private ReqFeature featureReq;
}
