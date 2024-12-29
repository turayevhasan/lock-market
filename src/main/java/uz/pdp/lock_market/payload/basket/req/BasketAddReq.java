package uz.pdp.lock_market.payload.basket.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketAddReq {
    @NotNull
    @Min(1)
    private Long lockId;
}
