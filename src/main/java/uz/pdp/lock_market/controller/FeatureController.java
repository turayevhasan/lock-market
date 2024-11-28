package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.FeatureAddReq;
import uz.pdp.lock_market.payload.feature.req.FeatureUpdateReq;
import uz.pdp.lock_market.payload.feature.res.FeatureRes;
import uz.pdp.lock_market.service.FeatureService;
import uz.pdp.lock_market.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.FEATURE)
public class FeatureController {
    private final FeatureService featureService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ApiResult<FeatureRes> addFeature(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid FeatureAddReq req) {
        return ApiResult.successResponse(featureService.add(lang, req));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ApiResult<FeatureRes> updateFeature(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id,
            @RequestBody FeatureUpdateReq req) {
        return ApiResult.successResponse(featureService.update(lang, id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<FeatureRes> getFeature(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id) {
        return ApiResult.successResponse(featureService.get(lang, id));
    }

    @GetMapping("/get-by-lock/{lockId}")
    public ApiResult<FeatureRes> getFeatureByLock(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("lockId") long lockId) {
        return ApiResult.successResponse(featureService.getByLock(lang, lockId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id) {
        return ApiResult.successResponse(featureService.delete(lang, id));
    }
}
