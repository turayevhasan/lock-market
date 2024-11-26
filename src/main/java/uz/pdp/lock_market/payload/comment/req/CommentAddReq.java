package uz.pdp.lock_market.payload.comment.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAddReq {
    @NotNull
    private Long lockId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer stars;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String text;
}
