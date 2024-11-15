package uz.pdp.lock_market.payload.lock.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lock_market.enums.LockType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LockAddReq {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long price;

    @NotNull
    private Long categoryId;

    @NotBlank
    private List<UUID> photoIds;

    @NotBlank
    private LockType lockType;
}
