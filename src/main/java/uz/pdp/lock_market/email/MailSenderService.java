package uz.pdp.lock_market.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.email.payload.SendEmailDto;
import uz.pdp.lock_market.util.CoreUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void send(SendEmailDto emailDto) {
        try {
            log.info("Sending email to {}", emailDto.getTo());

            emailDto.setFrom(CoreUtils.getIfExists(emailDto.getFrom(), "lock-market.uz"));

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailDto.getFrom());
            mimeMessageHelper.setTo(emailDto.getTo());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody(), emailDto.isHtml());

            javaMailSender.send(mimeMessage);

            log.info("Email sent to {}", emailDto.getTo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}