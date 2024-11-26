package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.app.req.ApplicationAddReq;
import uz.pdp.lock_market.payload.app.res.ApplicationRes;
import uz.pdp.lock_market.payload.app.req.ApplicationUpdateReq;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.service.ApplicationService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.APP)
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/add")
    public ApiResult<ResBaseMsg> addApp(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid ApplicationAddReq req) {
        return ApiResult.successResponse(applicationService.add(lang, req));
    }

    @PutMapping("/update/{id}")
    public ApiResult<ApplicationRes> updateApp(
            @PathVariable("id") long id,
            @RequestBody ApplicationUpdateReq req) {
        return ApiResult.successResponse(applicationService.update(id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<ApplicationRes> getApp(@PathVariable long id) {
        return ApiResult.successResponse(applicationService.getOne(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResult<List<ApplicationRes>> getAllApp(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean active) {
        return ApiResult.successResponse(applicationService.findAll(page, size, active));
    }
}
