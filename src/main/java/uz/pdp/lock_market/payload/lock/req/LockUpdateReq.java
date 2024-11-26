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
    private String nameUz;
    private String nameRu;
    private String nameEn;

    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;

    @Min(1)
    private Long price;

    private Boolean hasGift;

    @Min(1)
    private Long newPrice;

    private Long categoryId;

    private List<String> photos;

    private LockType lockType;
}
