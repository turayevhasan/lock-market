package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.PromoCode;
import uz.pdp.lock_market.payload.promo.req.PromoCodeUpdateReq;
import uz.pdp.lock_market.payload.promo.res.PromoCodeRes;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface PromoCodeMapper {

    static PromoCodeRes entityToDto(PromoCode promoCode) {
        return PromoCodeRes.builder()
                .promoCode(promoCode.getCode())
                .discountPrice(promoCode.getDiscountPrice())
                .active(promoCode.getActive())
                .createdAt(promoCode.getCreatedAt())
                .updatedAt(promoCode.getUpdatedAt())
                .build();
    }

    static void update(PromoCode promoCode, PromoCodeUpdateReq req) {
        promoCode.setCode(getIfExists(req.getCode(), promoCode.getCode()));
        promoCode.setDiscountPrice(getIfExists(req.getDiscountPrice(), promoCode.getDiscountPrice()));
        promoCode.setActive(getIfExists(req.getActive(), promoCode.getActive()));
    }
}
