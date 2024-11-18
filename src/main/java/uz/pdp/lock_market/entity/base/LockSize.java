package uz.pdp.lock_market.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LockSize {
    private double a;
    private double b;
    private double c;

    @Override
    public String toString() {
        return "a" + "b" + "c" ;
    }

    public double check(){
        return a + b + c;
    }

}
