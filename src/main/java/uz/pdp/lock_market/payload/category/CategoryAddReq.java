package uz.pdp.lock_market.payload.category;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryAddReq {
    @NotBlank
    private String name;
    private UUID photoId;
}
