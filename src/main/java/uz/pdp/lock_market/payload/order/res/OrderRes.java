package uz.pdp.lock_market.payload.order.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.pdp.lock_market.enums.OrderStatus;
import uz.pdp.lock_market.payload.order.req.CustomerDto;
import uz.pdp.lock_market.payload.order.req.OrderDetailDto;
import uz.pdp.lock_market.payload.promo.res.PromoCodeRes;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRes {
    private Long id;
    private CustomerDto customerDto;
    private OrderDetailDto orderDetailDto;
    private PromoCodeRes promoCode;
    private Long fullPrice;
    private OrderStatus orderStatus;
    private String check;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;

}