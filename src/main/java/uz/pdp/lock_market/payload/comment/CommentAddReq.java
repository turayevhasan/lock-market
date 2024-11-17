package uz.pdp.lock_market.payload.comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAddReq {
    private long lockId;
    @NotNull
    @Min(1)
    private Integer stars;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String text;
}
