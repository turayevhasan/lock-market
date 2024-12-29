package uz.pdp.lock_market.payload.basket.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketRes {
    private Long basketId;
    private Long lockId;
    private String lockName;
    private Boolean lockGift;
    private Long lockPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
