package uz.pdp.lock_market.payload.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderLineReq {
    @NotNull
    private Long lockId;

    @Min(1)
    @NotNull
    private Integer amount;
}
