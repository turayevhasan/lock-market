package uz.pdp.lock_market.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.*;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.enums.OrderStatus;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.OrderMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.order.req.CustomerDto;
import uz.pdp.lock_market.payload.order.req.OrderAddReq;
import uz.pdp.lock_market.payload.order.req.OrderDetailDto;
import uz.pdp.lock_market.payload.order.req.OrderLineReq;
import uz.pdp.lock_market.payload.order.res.OrderRes;
import uz.pdp.lock_market.repository.*;
import uz.pdp.lock_market.util.GlobalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final LockRepository lockRepository;
    private final MailService mailService;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final MessageSource messageSource;
    private final OrderDetailRepository orderDetailRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResBaseMsg addOrder(String lang, OrderAddReq req) {
        long fullPrice = 0;

        StringBuilder checkUz = new StringBuilder("<p><b>Qulflar: </b><br>");
        StringBuilder checkRu = new StringBuilder("<p><b>Замки: </b><br>");
        StringBuilder checkEn = new StringBuilder("<p><b>Locks: </b><br>");

        for (OrderLineReq lineReq : req.getOrderLines()) {
            Lock lock = lockRepository.findById(lineReq.getLockId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));
            fullPrice += lock.getNewPrice() * lineReq.getAmount();

            checkUz.append(lock.getNameUz()).append(" * ").append(lineReq.getAmount()).append(" = ").append(lock.getNewPrice() * lineReq.getAmount()).append("<br>");
            checkRu.append(lock.getNameRu()).append(" * ").append(lineReq.getAmount()).append(" = ").append(lock.getNewPrice() * lineReq.getAmount()).append("<br>");
            checkEn.append(lock.getNameEn()).append(" * ").append(lineReq.getAmount()).append(" = ").append(lock.getNewPrice() * lineReq.getAmount()).append("<br>");
        }


        PromoCode promoCode = null;
        if (req.getPromoCode() != null) {
            promoCode = promoCodeRepository.findByCode(req.getPromoCode())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.PROMOCODE_IS_NOT_VALID));

            if (promoCode.getActive()) {
                long fixedPrice = fullPrice - promoCode.getDiscountPrice();
                if (fixedPrice >= 0) {
                    fullPrice = fixedPrice;
                    checkUz.append("<b>Promokod chegirmasi:</b> -").append(promoCode.getDiscountPrice()).append("<br>");
                    checkRu.append("<b>Промокод cкидка:</b> -").append(promoCode.getDiscountPrice()).append("<br>");
                    checkEn.append("<b>Promocode discount:</b> -").append(promoCode.getDiscountPrice()).append("<br>");
                }
            }
        }
        checkEn.append("<b>Must be paid:</b> ").append(fullPrice).append("<br>");
        checkUz.append("<b>To'lanishi kerak:</b> ").append(fullPrice).append("<br>");
        checkRu.append("<b>Должно быть оплачено:</b> ").append(fullPrice).append("<br>");

        OrderDetailDto detailDto = req.getOrderDetailDto();
        CustomerDto customer = req.getCustomerDto();

        String paymentTypeUz = messageSource.getMessage(detailDto.getPaymentType().getKey(), null, Locale.of("uz"));
        checkUz.append("<b>Manzil:</b> ").append(detailDto.getCity()).append(", ").append(detailDto.getBranch()).append("<br>");
        checkUz.append("<b>To'lov turi:</b> ").append(paymentTypeUz).append("<br></p>");

        String paymentTypeRu = messageSource.getMessage(detailDto.getPaymentType().getKey(), null, Locale.of("ru"));
        checkRu.append("<b>Расположение:</b> ").append(detailDto.getCity()).append(", ").append(detailDto.getBranch()).append("<br>");
        checkRu.append("<b>Тип платежа:</b> ").append(paymentTypeRu).append("<br></p>");

        String paymentTypeEn = messageSource.getMessage(detailDto.getPaymentType().getKey(), null, Locale.of("en"));
        checkEn.append("<b>Location:</b> ").append(detailDto.getCity()).append(", ").append(detailDto.getBranch()).append("<br>");
        checkEn.append("<b>Payment type:</b> ").append(paymentTypeEn).append("<br></p>");

        OrderDetail orderDetail = OrderDetail.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .city(detailDto.getCity())
                .branch(detailDto.getBranch())
                .paymentType(detailDto.getPaymentType())
                .comment(detailDto.getComment())
                .installSoft(detailDto.getInstallSoft())
                .setupLock(detailDto.getSetupLock())
                .build();

        orderDetailRepository.save(orderDetail);//saved order detail

        Order order = Order.builder()
                .user(GlobalVar.getUser())
                .fullPrice(fullPrice)
                .promoCode(promoCode)
                .orderDetail(orderDetail)
                .checkUz(checkUz.toString())
                .checkRu(checkRu.toString())
                .checkEn(checkEn.toString())
                .lang(lang)
                .build();
        orderRepository.save(order); //order saved

        List<OrderLine> lines = new ArrayList<>();
        for (OrderLineReq line : req.getOrderLines()) {
            lines.add(new OrderLine(line.getLockId(), line.getAmount(), order));
        }
        orderLineRepository.saveAll(lines); //order lines saved

        UUID userId = GlobalVar.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED));
        basketRepository.deleteAll(user.getBaskets()); //deleted baskets
        return new ResBaseMsg(messageSource.getMessage("order.received", null, Locale.of(lang)));
    }

    public OrderRes getOne(String lang, long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        return OrderMapper.entityToDto(order, lang);
    }

    public OrderRes updateStatus(String lang, long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        if (order.getStatus().equals(status)) {
            throw RestException.restThrow(ErrorTypeEnum.ORDER_ALREADY_IN_THIS_STATUS);
        }

        if (order.getStatus().equals(OrderStatus.ARCHIVED)) {
            throw RestException.restThrow(ErrorTypeEnum.ORDER_ARCHIVED);
        }

        order.setStatus(status);
        orderRepository.save(order); //updated


        switch (order.getStatus()) {
            case OrderStatus.CONFIRMED -> {
                switch (order.getLang()) {
                    case "uz" -> sendMessage(order, order.getCheckUz(), "order.details", "order.confirmed");
                    case "ru" -> sendMessage(order, order.getCheckRu(), "order.details", "order.confirmed");
                    default -> sendMessage(order, order.getCheckEn(), "order.details", "order.confirmed");
                }
            }
            case OrderStatus.DELIVERED -> {
                String body = "<p>" + messageSource.getMessage("delivered.msg", null, Locale.of(order.getLang())) + order.getOrderDetail().getCity() + ", " + order.getOrderDetail().getBranch() + "</p>";
                sendMessage(order, body, "delivered.title", "delivered.success");
            }
            case OrderStatus.PAID -> {
                String body = messageSource.getMessage("paid.msg", null, Locale.of(order.getLang()));
                sendMessage(order, body, "paid.title", "paid.title");

                order.setStatus(OrderStatus.ARCHIVED);
                orderRepository.save(order); //updated
            }
            case OrderStatus.CANCELLED -> {
                String body = "<p>" + messageSource.getMessage("cancelled.msg", null, Locale.of(order.getLang())) + "</p>";
                sendMessage(order, body, "cancelled.title", "cancelled.subject");
            }
        }
        return OrderMapper.entityToDto(order, lang);
    }

    public List<OrderRes> getAll(String lang, int page, int size, OrderStatus status) {
        Pageable pageable = PageRequest.of(page, size);

        return orderRepository.findAllByStatus(status, pageable)
                .stream()
                .map(order -> OrderMapper.entityToDto(order, lang))
                .toList();
    }

    private void sendMessage(Order order, String body, String titleKey, String subjectKey) {
        mailService.sendMessage(
                order.getUser().getEmail(), body,
                messageSource.getMessage(titleKey, null, Locale.of(order.getLang())),
                messageSource.getMessage(subjectKey, null, Locale.of(order.getLang()))
        );
    }
}
