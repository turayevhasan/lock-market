package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Order;
import uz.pdp.lock_market.entity.OrderLine;
import uz.pdp.lock_market.payload.order.OrderAddReq;
import uz.pdp.lock_market.payload.order.OrderLineRes;
import uz.pdp.lock_market.payload.order.OrderRes;

import java.util.ArrayList;
import java.util.List;

public interface OrderMapper {
    static OrderRes entityToDto(Order order) {
        return OrderRes.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .customerSurname(order.getCustomerSurname())
                .customerPhone(order.getCustomerPhone())
                .customerEmail(order.getCustomerEmail())
                .deliveryType(order.getDeliveryType())
                .city(order.getCity())
                .branch(order.getBranch())
                .paymentType(order.getPaymentType())
                .setupLock(order.getSetupLock())
                .installSoft(order.getInstallSoft())
                .promoCode(order.getPromoCode())
                .fullPrice(order.getFullPrice())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .orderLines(linesToDto(order.getOrderLines()))
                .build();
    }

    static Order reqToEntity(OrderAddReq req, long fullPrice) {
        return Order.builder()
                .customerName(req.getCustomerName())
                .customerSurname(req.getCustomerSurname())
                .customerPhone(req.getCustomerPhone())
                .customerEmail(req.getCustomerEmail())
                .deliveryType(req.getDeliveryType())
                .city(req.getCity())
                .branch(req.getBranch())
                .paymentType(req.getPaymentType())
                .setupLock(req.getSetupLock())
                .installSoft(req.getInstallSoft())
                .promoCode(req.getPromoCode())
                .fullPrice(fullPrice)
                .build();
    }

    private static List<OrderLineRes> linesToDto(List<OrderLine> lines) {
        List<OrderLineRes> res = new ArrayList<>();
        for (OrderLine line : lines) {
            res.add(new OrderLineRes(line.getLockId(), line.getAmount()));
        }
        return res;
    }
}
