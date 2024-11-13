package uz.pdp.lock_market.controller;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.enums.OrderStatus;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.order.OrderAddReq;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.order.OrderRes;
import uz.pdp.lock_market.service.OrderService;
import uz.pdp.lock_market.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.ORDER)
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ApiResult<ResBaseMsg> create(@RequestBody OrderAddReq req) {
        return ApiResult.successResponse(orderService.addOrder(req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<OrderRes> get(@PathVariable("id") long id) {
        return ApiResult.successResponse(orderService.getOne(id));
    }

    @PutMapping("/update-status/{id}")
    public ApiResult<OrderRes> updateStatus(@PathVariable("id") long id,@RequestParam OrderStatus status) {
        return ApiResult.successResponse(orderService.updateStatus(id, status));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(@PathVariable("id") long id) {
        return ApiResult.successResponse(orderService.delete(id));
    }
}
