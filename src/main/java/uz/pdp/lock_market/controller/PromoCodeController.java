package uz.pdp.lock_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.promo.PromoCodeUpdateReq;
import uz.pdp.lock_market.payload.promo.PromoCodeAddReq;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.promo.PromoCodeRes;
import uz.pdp.lock_market.service.PromoCodeService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.PROMO_CODE)
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @PostMapping("/add")
    public ApiResult<PromoCodeRes> addPromoCode(@RequestBody PromoCodeAddReq req) {
        return ApiResult.successResponse(promoCodeService.add(req));
    }

    @PutMapping("/update/{id}")
    public ApiResult<PromoCodeRes> updatePromoCode(@PathVariable("id") long id, @RequestBody PromoCodeUpdateReq req) {
        return ApiResult.successResponse(promoCodeService.update(id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<PromoCodeRes> getPromoCode(@PathVariable("id") long id) {
        return ApiResult.successResponse(promoCodeService.getOne(id));
    }

    @GetMapping("/check-promo-code/{code}")
    public ApiResult<PromoCodeRes> checkPromoCode(@PathVariable String code){
        return ApiResult.successResponse(promoCodeService.checkPromoCode(code));
    }

    @GetMapping("/get-all")
    public ApiResult<List<PromoCodeRes>> getAllPromoCode(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Long discountPriceLessThan,
            @RequestParam(required = false) Long discountPriceMoreThan,
            @RequestParam(required = false) Boolean active) {
        return ApiResult.successResponse(promoCodeService.getAll(page, size, code, discountPriceLessThan, discountPriceMoreThan, active));
    }

}
