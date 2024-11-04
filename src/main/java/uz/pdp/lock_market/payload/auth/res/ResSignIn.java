package uz.pdp.lock_market.payload.auth.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResSignIn {
    private ResUserSimple user;
    private TokenDto token;
}
