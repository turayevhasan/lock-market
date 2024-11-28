package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Order;
import uz.pdp.lock_market.payload.order.req.CustomerDto;
import uz.pdp.lock_market.payload.order.req.OrderDetailDto;
import uz.pdp.lock_market.payload.order.res.OrderRes;

public interface OrderMapper {

    static OrderRes entityToDto(Order order, String lang) {
        return switch (lang) {
            case "uz" -> OrderRes.builder()
                    .id(order.getId())
                    .customerDto(new CustomerDto(order.getOrderDetail()))
                    .orderDetailDto(new OrderDetailDto(order.getOrderDetail()))
                    .promoCode(PromoCodeMapper.entityToDto(order.getPromoCode()))
                    .fullPrice(order.getFullPrice())
                    .check(order.getCheckUz())
                    .orderStatus(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
            case "ru" -> OrderRes.builder()
                    .id(order.getId())
                    .customerDto(new CustomerDto(order.getOrderDetail()))
                    .orderDetailDto(new OrderDetailDto(order.getOrderDetail()))
                    .promoCode(PromoCodeMapper.entityToDto(order.getPromoCode()))
                    .fullPrice(order.getFullPrice())
                    .check(order.getCheckRu())
                    .orderStatus(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
            default -> OrderRes.builder()
                    .id(order.getId())
                    .customerDto(new CustomerDto(order.getOrderDetail()))
                    .orderDetailDto(new OrderDetailDto(order.getOrderDetail()))
                    .promoCode(PromoCodeMapper.entityToDto(order.getPromoCode()))
                    .fullPrice(order.getFullPrice())
                    .check(order.getCheckEn())
                    .orderStatus(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
        };
    }
}
