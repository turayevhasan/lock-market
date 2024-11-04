package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.email.MailSenderService;
import uz.pdp.lock_market.email.payload.SendEmailDto;
import uz.pdp.lock_market.entity.User;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSenderService mailSenderService;

    public void sendActivationEmail(User user) {

        String url = "http://localhost:8080/api/v1/auth/activate/" + user.getEmail();

        SendEmailDto sendEmailDto = SendEmailDto.builder()
                .to(user.getEmail())
                .subject("Activation Email For CodingBat")
                .body("<a href=\"%s\">CLICK_LINK</a>".formatted(url))
                .html(true)
                .build();
        mailSenderService.send(sendEmailDto);
    }
}
