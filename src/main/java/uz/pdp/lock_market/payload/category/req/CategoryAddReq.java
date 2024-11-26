package uz.pdp.lock_market.payload.category.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryAddReq {
    @NotBlank
    private String nameUz;

    @NotBlank
    private String nameEn;

    @NotBlank
    private String nameRu;

    @NotNull
    private String photoPath;
}
