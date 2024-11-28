package uz.pdp.lock_market.payload.app.req;

import lombok.Data;

@Data
public class ApplicationUpdateReq {
    private String name;
    private String company;
    private String phone;
    private Long lockId;
    private Integer lockAmount;
    private Boolean customLogo;
    private Boolean helpSetup;
    private Boolean active;
}
