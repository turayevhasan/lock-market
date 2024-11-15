package uz.pdp.lock_market.payload.comment;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateReq {
    private String name;
    private String email;
    @Min(1)
    private Integer stars;
    private String text;
}
