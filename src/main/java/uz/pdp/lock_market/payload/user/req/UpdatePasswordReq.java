package uz.pdp.lock_market.payload.user.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordReq {
    @NotBlank
    @Size(min = 6, max = 20)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String confirmNewPassword;
}
