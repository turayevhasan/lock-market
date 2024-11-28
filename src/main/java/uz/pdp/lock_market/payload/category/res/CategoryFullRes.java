package uz.pdp.lock_market.payload.category.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryFullRes {
    private Long id;
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private String photoPath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
