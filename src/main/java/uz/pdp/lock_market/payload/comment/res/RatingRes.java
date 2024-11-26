package uz.pdp.lock_market.payload.comment.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingRes {
    private Integer star;
    private Integer comments;
}
