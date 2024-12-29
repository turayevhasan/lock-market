package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.auth.req.RefreshTokenReq;
import uz.pdp.lock_market.payload.auth.req.SignInReq;
import uz.pdp.lock_market.payload.auth.req.SignUpReq;
import uz.pdp.lock_market.payload.auth.req.VerifyAccountReq;
import uz.pdp.lock_market.payload.auth.res.SignInRes;
import uz.pdp.lock_market.payload.auth.res.TokenDto;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.service.AuthService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.Locale;


@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResult<ResBaseMsg> signUp(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid SignUpReq req) {
        return ApiResult.successResponse(authService.signUp(lang, req));
    }

    @PostMapping("/verify-account")
    public ApiResult<ResBaseMsg> verification(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid VerifyAccountReq req) {
        return ApiResult.successResponse(authService.verifyAccount(lang, req));
    }

    @PostMapping("/sign-in")
    ApiResult<SignInRes> signIn(@Valid @RequestBody SignInReq req) {
        return ApiResult.successResponse(authService.signIn(req));
    }

    @PostMapping("/refresh-token")
    ApiResult<TokenDto> refreshToken(@Valid @RequestBody RefreshTokenReq req) {
        return ApiResult.successResponse(authService.refreshToken(req));
    }
}

