package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.FeatureAddReq;
import uz.pdp.lock_market.payload.feature.req.FeatureUpdateReq;
import uz.pdp.lock_market.payload.feature.res.ResFeature;
import uz.pdp.lock_market.service.FeatureService;
import uz.pdp.lock_market.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.FEATURE)
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping("/add")
    public ApiResult<ResFeature> addFeature(@RequestBody @Valid FeatureAddReq req) {
        return ApiResult.successResponse(featureService.add(req));
    }

    @PutMapping("/update/{id}")
    public ApiResult<ResFeature> updateFeature(@PathVariable("id") long id, @RequestBody FeatureUpdateReq req) {
        return ApiResult.successResponse(featureService.update(id,req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<ResFeature> getFeature(@PathVariable("id") long id) {
        return ApiResult.successResponse(featureService.get(id));
    }

    @GetMapping("/get-by-lock/{lockId}")
    public ApiResult<ResFeature> getFeatureByLock(@PathVariable("lockId") long lockId) {
        return ApiResult.successResponse(featureService.getByLock(lockId));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(@PathVariable("id") long id) {
        return ApiResult.successResponse(featureService.delete(id));
    }
}
