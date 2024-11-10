package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;
import uz.pdp.lock_market.payload.feature.res.ResFeature;
import uz.pdp.lock_market.service.FeatureService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;


@RequestMapping(BaseURI.API1 + BaseURI.FEATURE)
@RestController
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping("/add")
    public ApiResult<ResFeature> add(@RequestBody @Valid ReqFeature req) {
        return ApiResult.successResponse(featureService.add(req));
    }

    @GetMapping( "/update/{id}")
    public ApiResult<ResFeature> update(@PathVariable Long id, @RequestBody @Valid ReqFeature req) {
        return ApiResult.successResponse(featureService.update(id,req));
    }

    @PostMapping("/delete/{id}")
    ApiResult<ResBaseMsg> delete(@PathVariable Long id) {
        return ApiResult.successResponse(featureService.delete(id));
    }

    @GetMapping("/get/{id}")
    ApiResult<ResFeature> get(@PathVariable long id) {
        return ApiResult.successResponse(featureService.get(id));
    }

    @GetMapping("/get-all")
    ApiResult<List<ResFeature>> getAll() {
        return ApiResult.successResponse(featureService.getAll());
    }
}
