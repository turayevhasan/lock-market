package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Basket;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.BasketMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.basket.req.BasketAddReq;
import uz.pdp.lock_market.payload.basket.res.BasketRes;
import uz.pdp.lock_market.repository.BasketRepository;
import uz.pdp.lock_market.repository.LockRepository;
import uz.pdp.lock_market.repository.UserRepository;
import uz.pdp.lock_market.util.GlobalVar;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final ResourceBundleMessageSource messageSource;
    private final LockRepository lockRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    public ResBaseMsg add(String lang, BasketAddReq req) {
        Lock lock = lockRepository.findById(req.getLockId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        List<Basket> baskets = userRepository.findById(GlobalVar.getUser().getId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND))
                .getBaskets();

        for (Basket basket : baskets) {
            if (basket.getLock().equals(lock)) {
                throw RestException.restThrow(ErrorTypeEnum.BASKET_ALREADY_EXISTS);
            }
        }
        Basket basket = new Basket(lock, GlobalVar.getUser());
        basketRepository.save(basket);

        return new ResBaseMsg(messageSource.getMessage("lock.added.basket", null, Locale.of(lang)));
    }

    public List<BasketRes> myBasket(String lang) {
        return userRepository.findById(GlobalVar.getUser().getId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND))
                .getBaskets()
                .stream()
                .map(basket -> BasketMapper.entityToRes(basket, lang))
                .toList();
    }

    public ResBaseMsg delete(Long id, String lang) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.BASKET_NOT_FOUND));

        basketRepository.delete(basket);
        return new ResBaseMsg(messageSource.getMessage("basket.deleted", null, Locale.of(lang)));
    }
}
