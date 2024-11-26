package uz.pdp.lock_market.payload.comment.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateReq {
    private String name;
    private String email;
    @Min(1)
    @Max(5)
    private Integer stars;
    private String text;
}
