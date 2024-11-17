package uz.pdp.lock_market.payload.auth.req;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenReq {

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String accessToken;
}
