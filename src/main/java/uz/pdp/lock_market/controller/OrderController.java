package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.enums.OrderStatus;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.order.req.OrderAddReq;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.order.res.OrderRes;
import uz.pdp.lock_market.service.OrderService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.ORDER)
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> create(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid OrderAddReq req) {
        return ApiResult.successResponse(orderService.addOrder(lang, req));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/{id}")
    public ApiResult<OrderRes> get(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id) {
        return ApiResult.successResponse(orderService.getOne(lang, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-status/{id}")
    public ApiResult<OrderRes> updateStatus(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id,
            @RequestParam OrderStatus status) {
        return ApiResult.successResponse(orderService.updateStatus(lang, id, status));
    }
}
