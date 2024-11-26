package uz.pdp.lock_market.payload.order.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.lock_market.entity.OrderDetail;
import uz.pdp.lock_market.enums.PaymentType;

@Data
public class OrderDetailDto {
    @NotBlank
    private String city;

    @NotBlank
    private String branch;

    @NotBlank
    private PaymentType paymentType;

    @NotNull
    private Boolean setupLock;

    @NotNull
    private Boolean installSoft;

    private String comment;

    public OrderDetailDto(OrderDetail orderOrderDetail) {
        this.city = orderOrderDetail.getCity();
        this.branch = orderOrderDetail.getBranch();
        this.paymentType = orderOrderDetail.getPaymentType();
        this.setupLock = orderOrderDetail.getSetupLock();
        this.installSoft = orderOrderDetail.getInstallSoft();
        this.comment = orderOrderDetail.getComment();
    }
}
