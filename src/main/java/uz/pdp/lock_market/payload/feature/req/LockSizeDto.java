package uz.pdp.lock_market.payload.feature.req;

import lombok.Data;

@Data
public class LockSizeDto {
    private Double a;

    private Double b;

    private Double c;

    public LockSizeDto(Double a, Double b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
