package uz.pdp.lock_market.payload.feature.req;

import lombok.Data;

@Data
public class BatteryDto {
    private Double voltage;
    private Double ampere;

    public BatteryDto(Double voltage, Double ampere) {
        this.voltage = voltage;
        this.ampere = ampere;
    }
}
