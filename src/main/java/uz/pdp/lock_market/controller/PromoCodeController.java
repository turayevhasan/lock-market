package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.promo.req.PromoCodeUpdateReq;
import uz.pdp.lock_market.payload.promo.req.PromoCodeAddReq;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.promo.res.PromoCodeRes;
import uz.pdp.lock_market.service.PromoCodeService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.PROMO_CODE)
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ApiResult<PromoCodeRes> addPromoCode(@RequestBody @Valid PromoCodeAddReq req) {
        return ApiResult.successResponse(promoCodeService.add(req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ApiResult<PromoCodeRes> updatePromoCode(@PathVariable("id") long id, @RequestBody @Valid PromoCodeUpdateReq req) {
        return ApiResult.successResponse(promoCodeService.update(id, req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{id}")
    public ApiResult<PromoCodeRes> getPromoCode(@PathVariable("id") long id) {
        return ApiResult.successResponse(promoCodeService.getOne(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResult<List<PromoCodeRes>> getAllPromoCode(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long minDiscountPrice,
            @RequestParam(required = false) Long maxDiscountPrice,
            @RequestParam(required = false) Boolean active) {
        return ApiResult.successResponse(promoCodeService.getAll(page, size, minDiscountPrice, maxDiscountPrice, active));
    }

}
