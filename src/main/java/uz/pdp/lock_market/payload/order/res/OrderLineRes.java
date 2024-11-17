package uz.pdp.lock_market.payload.order.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineRes {
    private Long lockId;
    private int amount;
}
