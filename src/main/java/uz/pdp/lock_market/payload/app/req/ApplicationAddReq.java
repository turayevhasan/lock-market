package uz.pdp.lock_market.payload.app.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationAddReq {
    @NotBlank
    private String name;
    @NotBlank
    private String company;
    @NotBlank
    private String phone;
    @NotNull
    @Min(0)
    private Long lockId;
    @NotNull
    @Min(1)
    private Integer lockAmount;
    @NotNull
    private Boolean customLogo;
    @NotNull
    private Boolean helpSetup;
}
