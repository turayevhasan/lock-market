package uz.pdp.lock_market.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.config.UserPrincipal;
import uz.pdp.lock_market.config.jwt.JwtTokenProvider;
import uz.pdp.lock_market.entity.Role;
import uz.pdp.lock_market.entity.User;
import uz.pdp.lock_market.enums.RoleEnum;
import uz.pdp.lock_market.enums.UserStatus;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.payload.auth.req.ReqRefreshToken;
import uz.pdp.lock_market.payload.auth.req.ReqSignIn;
import uz.pdp.lock_market.payload.auth.req.ReqSignUp;
import uz.pdp.lock_market.payload.auth.res.ResSignIn;
import uz.pdp.lock_market.payload.auth.res.ResUserSimple;
import uz.pdp.lock_market.payload.auth.res.TokenDto;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.repository.RoleRepository;
import uz.pdp.lock_market.repository.UserRepository;
import uz.pdp.lock_market.util.BaseConstants;

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

    public ResBaseMsg signUp(ReqSignUp req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw RestException.restThrow(EMAIL_ALREADY_EXISTS);

        Role role = roleRepository.findByName(RoleEnum.USER.name())
                .orElseThrow(() -> RestException.restThrow(ROLE_NOT_FOUND));

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        try {
            String link = "http://localhost:8080/api/v1/auth/activate/" + user.getEmail();
            String body = "<a href=\"%s\">CLICK_TO_CONFIRM</a>".formatted(link);
            mailService.sendMessage(user.getEmail(), body, "Please complete registration", "Complete Registration");
        } catch (MessagingException e) {
            throw RestException.restThrow(EMAIL_NOT_VALID);
        }

        userRepository.save(user);

        return new ResBaseMsg("Success! Verification Sms sent your Email!");
    }

    public ResBaseMsg verifyEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(RestException.thew(USER_NOT_FOUND));

        if (user.isActive())
            return new ResBaseMsg("User Already Verified!");

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return new ResBaseMsg("User Successfully Verified!");
    }


    public ResSignIn signIn(ReqSignIn req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                ));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return generateSignInRes(userPrincipal.user());
    }

    public TokenDto refreshToken(ReqRefreshToken req) {
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

    private ResSignIn generateSignInRes(User user) {
        ResUserSimple resUserSimple = new ResUserSimple(user);
        return new ResSignIn(resUserSimple, generateTokens(user));
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
}
