package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.basket.req.BasketAddReq;
import uz.pdp.lock_market.payload.basket.res.BasketRes;
import uz.pdp.lock_market.service.BasketService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.BASKET)
public class BasketController {

    private final BasketService basketService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> add(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid BasketAddReq req) {
        return ApiResult.successResponse(basketService.add(lang, req));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-basket")
    public ApiResult<List<BasketRes>> getMyBasket(
            @RequestHeader(value = "Accept-Language", required = false) String lang) {
        return ApiResult.successResponse(basketService.myBasket(lang));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ApiResult<ResBaseMsg> delete(
            @PathVariable("id") Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang) {
        return ApiResult.successResponse(basketService.delete(id, lang));
    }
}
