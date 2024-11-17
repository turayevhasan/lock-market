package uz.pdp.lock_market.payload.order.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.pdp.lock_market.util.FormatPatterns;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRes {
    private Long orderId;
    private String customerName;
    private String customerSurname;
    private String customerPhone;
    private String customerEmail;
    private String deliveryType;
    private String city;
    private String branch;
    private String paymentType;
    private boolean setupLock;
    private boolean installSoft;
    private String promoCode;
    private long fullPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;

    private List<OrderLineRes> orderLines;
}