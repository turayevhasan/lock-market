package uz.pdp.lock_market.payload.auth.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SignInReq {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
