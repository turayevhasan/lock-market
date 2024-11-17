package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;
import uz.pdp.lock_market.payload.feature.res.ResFeature;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.service.FeatureService;
import uz.pdp.lock_market.service.LockService;
import uz.pdp.lock_market.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.FEATURE)
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping("/add")
    public ApiResult<ResFeature> addLock(@RequestBody @Valid ReqFeature req) {
        return ApiResult.successResponse(featureService.add(req));
    }

    @PutMapping("/update/{id}")
    public ApiResult<ResFeature> updateLock(@PathVariable("id") long id, @RequestBody ReqFeature req) {
        return ApiResult.successResponse(featureService.update(id,req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<ResFeature> getLock(@PathVariable("id") long id) {
        return ApiResult.successResponse(featureService.get(id));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(@PathVariable("id") long id) {
        return ApiResult.successResponse(featureService.delete(id));
    }
}
