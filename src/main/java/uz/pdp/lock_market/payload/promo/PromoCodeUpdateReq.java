package uz.pdp.lock_market.payload.promo;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PromoCodeUpdateReq {
    private String code;
    private Boolean active;

    @Min(1)
    private Long discountPrice;
}
