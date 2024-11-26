package uz.pdp.lock_market.payload.feature.req;

import lombok.Data;

@Data
public class DoorWidthDto {
    private Double a;
    private Double b;

    public DoorWidthDto(Double a, Double b) {
        this.a = a;
        this.b = b;
    }
}
