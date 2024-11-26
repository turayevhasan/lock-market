package uz.pdp.lock_market.payload.category.req;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryUpdateReq {
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private String photoPath;
}
