package uz.pdp.lock_market.payload.order.req;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerSurname;
    @NotBlank
    private String customerPhone;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private String deliveryType;
    @NotBlank
    private String city;
    @NotBlank
    private String branch;
    @NotBlank
    private String paymentType;
    @NotNull
    private Boolean setupLock;
    @NotNull
    private Boolean installSoft;
    private String promoCode;
}

