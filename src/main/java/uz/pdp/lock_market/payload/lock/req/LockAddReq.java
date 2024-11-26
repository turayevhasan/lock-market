package uz.pdp.lock_market.payload.lock.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.enums.LockType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LockAddReq {
    @NotBlank
    private String nameUz;

    @NotBlank
    private String nameEn;

    @NotBlank
    private String nameRu;

    @NotBlank
    private String descriptionUz;

    @NotBlank
    private String descriptionEn;

    @NotBlank
    private String descriptionRu;

    @NotNull
    @Min(1)
    private Long price;

    @NotNull
    @Min(1)
    private Long newPrice;

    @NotNull
    private Long categoryId;

    @NotNull
    private List<String> photos;

    @NotNull
    private LockType lockType;

    @NotNull
    private Boolean hasGift;
}
