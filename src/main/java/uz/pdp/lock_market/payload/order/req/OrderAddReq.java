package uz.pdp.lock_market.payload.order.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderAddReq {
    @NotEmpty
    private List<OrderLineReq> orderLines;

    @NotNull
    private CustomerDto customerDto;

    @NotNull
    private OrderDetailDto orderDetailDto;

    private String promoCode;
}

