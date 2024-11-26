package uz.pdp.lock_market.service;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.config.web.UserPrincipal;
import uz.pdp.lock_market.config.jwt.JwtTokenProvider;
import uz.pdp.lock_market.entity.Role;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.enums.RoleEnum;
import uz.pdp.lock_market.enums.UserStatus;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.UserMapper;
import uz.pdp.lock_market.payload.auth.req.RefreshTokenReq;
import uz.pdp.lock_market.payload.auth.req.SignInReq;
import uz.pdp.lock_market.payload.auth.req.SignUpReq;
import uz.pdp.lock_market.payload.auth.req.VerifyAccountReq;
import uz.pdp.lock_market.payload.auth.res.SignInRes;
import uz.pdp.lock_market.payload.auth.res.TokenDto;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.user.res.UserRes;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.repository.RoleRepository;
import uz.pdp.lock_market.repository.UserRepository;
import uz.pdp.lock_market.util.BaseConstants;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uz.pdp.lock_market.enums.ErrorTypeEnum.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CodeService codeService;
    private final AttachmentRepository attachmentRepository;
    private final MessageSource messageSource;

    public ResBaseMsg signUp(String lang, SignUpReq req) {
        Pattern pattern = Pattern.compile("^\\w*?[a-zA-Z]\\w+@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.[a-z]+\\z");
        Matcher matcher = pattern.matcher(req.getEmail());

        if (!matcher.matches())
            throw RestException.restThrow(EMAIL_NOT_VALID);

        if (userRepository.existsByEmail(req.getEmail()))
            throw RestException.restThrow(EMAIL_ALREADY_EXISTS);

        Role role = roleRepository.findByName(RoleEnum.USER.name())
                .orElseThrow(() -> RestException.restThrow(ROLE_NOT_FOUND));

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        String code = codeService.generateCode(req.getEmail());
        String body = String.format("<p class=\"code\">%s</p>", code);

        mailService.sendMessage(
                user.getEmail(), body,
                messageSource.getMessage("send.code.msg", null, Locale.of(lang)),
                messageSource.getMessage("complete.registration", null, Locale.of(lang))
        );

        return new ResBaseMsg(messageSource.getMessage("auth.confirm.msg", null, Locale.of(lang)));
    }


    public SignInRes signIn(SignInReq req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                ));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (!userPrincipal.user().isActive())
            throw RestException.restThrow(USER_NOT_ACTIVATED);

        return generateSignInRes(userPrincipal.user());
    }

    public TokenDto refreshToken(RefreshTokenReq req) {
        String accessToken = req.getAccessToken().trim();
        accessToken = getTokenWithOutBearer(accessToken);

        try {
            jwtTokenProvider.extractUsername(accessToken);
        } catch (ExpiredJwtException ex) {
            try {
                String refreshToken = req.getRefreshToken();
                refreshToken = getTokenWithOutBearer(refreshToken);

                String email = jwtTokenProvider.extractUsername(refreshToken);
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> RestException.restThrow(TOKEN_NOT_VALID));

                if (!user.isActive())
                    throw RestException.restThrow(USER_PERMISSION_RESTRICTION);

                return generateTokens(user);

            } catch (Exception e) {
                throw RestException.restThrow(REFRESH_TOKEN_EXPIRED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow(WRONG_ACCESS_TOKEN);
        }
        throw RestException.restThrow(ACCESS_TOKEN_NOT_EXPIRED);
    }

    private static String getTokenWithOutBearer(String token) {
        return token.startsWith(BaseConstants.BEARER_TOKEN) ?
                token.substring(token.indexOf(BaseConstants.BEARER_TOKEN) + 6).trim() :
                token.trim();
    }

    private SignInRes generateSignInRes(User user) {
        UserRes userRes = UserMapper.entityToRes(user);
        return new SignInRes(userRes, generateTokens(user));
    }

    private TokenDto generateTokens(User user) {
        String accessToken = jwtTokenProvider.generateToken(user);
        Long accessTokenExp = BaseConstants.ACCESS_TOKEN_EXPIRE;
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        Long refreshTokenExp = BaseConstants.REFRESH_TOKEN_EXPIRE;

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpire(accessTokenExp)
                .refreshTokenExpire(refreshTokenExp)
                .build();
    }

    public ResBaseMsg verifyAccount(String lang, VerifyAccountReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(RestException.thew(USER_NOT_FOUND));

        if (user.isActive())
            return new ResBaseMsg(messageSource.getMessage("already.verified", null, Locale.of(lang)));

        codeService.checkVerify(req.getEmail(), req.getCode());

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return new ResBaseMsg(messageSource.getMessage("account.verified", null, Locale.of(lang)));
    }
}
