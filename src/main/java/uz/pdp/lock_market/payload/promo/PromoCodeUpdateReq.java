package uz.pdp.lock_market.payload.promo;

import lombok.Data;

@Data
public class PromoCodeUpdateReq {
    private String code;
    private Boolean active;
    private Long discountPrice;
}
