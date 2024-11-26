package uz.pdp.lock_market.payload.user.req;

import lombok.Data;

import java.util.UUID;

@Data
public class ProfileUpdateReq {
    private String name;
    private String photoPath;
}
