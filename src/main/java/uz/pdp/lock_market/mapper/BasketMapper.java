package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Basket;
import uz.pdp.lock_market.payload.basket.res.BasketRes;

public interface BasketMapper {
    static BasketRes entityToRes(Basket basket, String lang) {
        BasketRes res = BasketRes.builder()
                .basketId(basket.getId())
                .lockId(basket.getLock().getId())
                .lockGift(basket.getLock().getHasGift())
                .lockPrice(basket.getLock().getNewPrice())
                .createdAt(basket.getCreatedAt())
                .updatedAt(basket.getUpdatedAt())
                .build();

        switch (lang) {
            case "uz" -> res.setLockName(basket.getLock().getNameUz());
            case "ru" -> res.setLockName(basket.getLock().getNameRu());
            default -> res.setLockName(basket.getLock().getNameEn());
        }
        return res;
    }
}
