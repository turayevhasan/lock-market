package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.UserMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.user.req.ProfileUpdateReq;
import uz.pdp.lock_market.payload.user.req.ResetPasswordReq;
import uz.pdp.lock_market.payload.user.req.UpdatePasswordReq;
import uz.pdp.lock_market.payload.user.res.UserRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.UserRepository;
import uz.pdp.lock_market.util.GlobalVar;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;
    private final MessageSource messageSource;

    public ResBaseMsg forgotPassword(String lang, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (!user.isActive())
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_ACTIVATED);

        String code = codeService.generateCode(email);
        String body = String.format("<p class=\"code\">%s</p>", code);
        mailService.sendMessage(
                email, body,
                messageSource.getMessage("get.code", null, Locale.of(lang)),
                messageSource.getMessage("forgot.password", null, Locale.of(lang))
        );

        return new ResBaseMsg(messageSource.getMessage("we.send.code", null, Locale.of(lang)));
    }

    public ResBaseMsg changePassword(String lang, UpdatePasswordReq req) {
        User user = GlobalVar.getUser();

        if (user == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword()))
            throw RestException.restThrow(ErrorTypeEnum.OLD_PASSWORD_NOT_MATCH);

        if (!req.getNewPassword().equals(req.getConfirmNewPassword()))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user); //updated

        return new ResBaseMsg(messageSource.getMessage("password.updated", null, Locale.of(lang)));
    }

    public ResBaseMsg resetPassword(String lang, ResetPasswordReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (!user.isActive())
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_ACTIVATED);

        if (!req.getNewPassword().equals(req.getConfirmNewPassword()))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        codeService.checkVerify(req.getEmail(), req.getCode()); //checking

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user); //updated

        return new ResBaseMsg(messageSource.getMessage("password.updated", null, Locale.of(lang)));
    }

    public UserRes update(ProfileUpdateReq req) {
        if (GlobalVar.getUser() == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

        User user = GlobalVar.getUser();

        if (req.getPhotoPath() != null) {
            if (!Files.exists(Path.of(req.getPhotoPath()))) {
                throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
            }
            user.setPhotoPath(req.getPhotoPath());
        }
        if (req.getName() != null)
            user.setName(req.getName());

        userRepository.save(user); //saved
        return UserMapper.entityToRes(user);
    }

}
