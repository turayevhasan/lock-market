package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.entity.LockSize;
import uz.pdp.lock_market.enums.Color;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.lock.req.LockAddReq;
import uz.pdp.lock_market.payload.lock.req.LockUpdateReq;
import uz.pdp.lock_market.payload.lock.res.LockRes;
import uz.pdp.lock_market.service.LockService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.LOCK)
public class LockController {
    private final LockService lockService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ApiResult<LockRes> addLock(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid LockAddReq req) {
        return ApiResult.successResponse(lockService.add(lang, req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ApiResult<LockRes> updateLock(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id,
            @RequestBody @Valid LockUpdateReq req) {
        return ApiResult.successResponse(lockService.update(lang, id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<LockRes> getLock(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id) {
        return ApiResult.successResponse(lockService.get(lang, id));
    }

    @GetMapping("/get-all-by-filter/{categoryId}")
    public ApiResult<List<LockRes>> getAllLockByCategory(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long startPrice,
            @RequestParam(required = false) Long endPrice,
            @RequestParam(required = false) Color color,
            @RequestParam(required = false) LockSize lockSize,
            @RequestParam(required = false) String material) {
        return ApiResult.successResponse(lockService.getAllByCategory(lang, categoryId, page, size, startPrice, endPrice, color, lockSize, material));
    }
}
