package uz.pdp.lock_market.payload.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryUpdateReq {
    private String name;
    private UUID photoId;
}
