package uz.pdp.lock_market.payload.mail;

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