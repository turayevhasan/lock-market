package uz.pdp.lock_market.payload.promo.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromoCodeAddReq {
    @NotBlank
    private String code;
    @NotNull
    @Min(0)
    private Long discountPrice;
}
