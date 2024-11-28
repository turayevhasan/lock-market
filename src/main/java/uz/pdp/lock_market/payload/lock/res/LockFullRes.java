package uz.pdp.lock_market.payload.lock.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockFullRes {
    private Long id;

    private String nameUz;
    private String nameRu;
    private String nameEn;

    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;

    private Long price;

    private Long newPrice;

    private Long categoryId;

    private Boolean hasGift;

    private List<String> photos;

    private String lockType;

    private boolean deleted;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
