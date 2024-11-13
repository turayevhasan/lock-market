package uz.pdp.lock_market.payload.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRes {
    private Long id;
    private String name;
    private String company;
    private String phone;
    private Long lockId; //todo LockRes implementation here
    private Integer lockAmount;
    private Boolean customLogo;
    private Boolean helpSetup;
    private Boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
