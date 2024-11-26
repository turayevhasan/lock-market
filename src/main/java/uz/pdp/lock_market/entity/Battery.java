package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;
import uz.pdp.lock_market.payload.feature.req.BatteryDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "battery")
public class Battery extends TimeLong {
    @Column(nullable = false)
    private Double voltage;

    @Column(nullable = false)
    private Double ampere;

    public Battery(BatteryDto req){
        this.voltage = req.getVoltage();
        this.ampere = req.getAmpere();
    }

    public BatteryDto getDto(){
        return new BatteryDto(this.voltage, this.ampere);
    }
}
