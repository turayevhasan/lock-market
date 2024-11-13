package uz.pdp.lock_market.email.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailDto {
    private String to;
    private String subject;
    private String body;
    private boolean html;
}