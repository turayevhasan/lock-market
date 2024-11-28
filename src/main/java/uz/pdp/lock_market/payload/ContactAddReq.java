package uz.pdp.lock_market.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactAddReq {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
}
