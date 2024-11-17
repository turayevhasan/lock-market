package uz.pdp.lock_market.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.entity.Order;
import uz.pdp.lock_market.entity.OrderLine;
import uz.pdp.lock_market.entity.PromoCode;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.enums.OrderStatus;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.OrderMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.order.req.OrderAddReq;
import uz.pdp.lock_market.payload.order.req.OrderLineReq;
import uz.pdp.lock_market.payload.order.res.OrderRes;
import uz.pdp.lock_market.repository.LockRepository;
import uz.pdp.lock_market.repository.OrderLineRepository;
import uz.pdp.lock_market.repository.OrderRepository;
import uz.pdp.lock_market.repository.PromoCodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final LockRepository lockRepository;
    private final MailService mailService;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final PromoCodeRepository promoCodeRepository;

    public ResBaseMsg addOrder(OrderAddReq req) {
        long fullPrice = 0;
        StringBuilder check = new StringBuilder("<b>Locks: <b><br>");

        for (OrderLineReq line : req.getOrderLines()) {
            Lock lock = lockRepository.findById(line.getLockId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

            fullPrice += lock.getPrice() * line.getAmount();
            check.append(lock.getName()).append(" * ").append(line.getAmount()).append(" = ").append(lock.getPrice() * line.getAmount()).append("<br>");
        }

        Optional<PromoCode> promoCode = promoCodeRepository.findByCode(req.getPromoCode());
        if (promoCode.isPresent() && promoCode.get().getActive()) {
            long fixedPrice = fullPrice - promoCode.get().getDiscountPrice();
            if (fixedPrice >= 0)
                fullPrice = fixedPrice;
        }

        check = new StringBuilder("Full price: " + fullPrice + "<br>");
        check.append("Location: ").append(req.getCity()).append(", ").append(req.getBranch()).append("<br>");
        check.append("Delivery type: ").append(req.getDeliveryType()).append("<br>");
        check.append("Payment type: ").append(req.getPaymentType()).append("<br>");

        try {
            mailService.sendMessage(req.getCustomerEmail(), check.toString(), "Your order details", "Order Confirmed"); //send order confirmation
        } catch (MessagingException e) {
            throw RestException.restThrow(ErrorTypeEnum.EMAIL_NOT_VALID);
        }

        Order order = OrderMapper.reqToEntity(req, fullPrice);
        orderRepository.save(order);

        List<OrderLine> lines = new ArrayList<>();
        for (OrderLineReq line : req.getOrderLines()) {
            lines.add(new OrderLine(line.getLockId(), line.getAmount(), order));
        }
        orderLineRepository.saveAll(lines);

        return new ResBaseMsg("Successfully! We will send order confirmation to " + req.getCustomerEmail());
    }

    public OrderRes getOne(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        return OrderMapper.entityToDto(order);
    }

    public OrderRes updateStatus(long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        if (order.getStatus().equals(status)) {
            throw RestException.restThrow(ErrorTypeEnum.ORDER_ALREADY_IN_THIS_STATUS);
        }

        order.setStatus(status);
        orderRepository.save(order); //updated

        try {
            switch (order.getStatus()) {
                case OrderStatus.DELIVERED -> {
                    String body = "Please go to this address to receive your order: " + order.getCity() + ", " + order.getBranch();
                    mailService.sendMessage(order.getCustomerEmail(), body, "Successfully delivered your order!", "Order Delivered");

                    order.setStatus(OrderStatus.ARCHIVED);
                    orderRepository.save(order);
                }
                case OrderStatus.CANCELLED -> {
                    String body = "Your order has ben cancelled due to some problems";
                    mailService.sendMessage(order.getCustomerEmail(), body, "Your order cancelled", "Order Cancelled");
                }
            }
        } catch (MessagingException e) {
            throw RestException.restThrow(ErrorTypeEnum.EMAIL_NOT_VALID);
        }

        return OrderMapper.entityToDto(order);
    }


    public ResBaseMsg delete(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        orderLineRepository.deleteAllByOrderId(order.getId());
        orderRepository.delete(order);

        return new ResBaseMsg("Order successfully deleted!");
    }
}
