package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.auth.req.ReqRefreshToken;
import uz.pdp.lock_market.payload.auth.req.ReqSignIn;
import uz.pdp.lock_market.payload.auth.req.ReqSignUp;
import uz.pdp.lock_market.payload.auth.res.ResSignIn;
import uz.pdp.lock_market.payload.auth.res.TokenDto;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.service.AuthService;
import uz.pdp.lock_market.util.BaseURI;


@RequestMapping(BaseURI.API1 + BaseURI.AUTH)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResult<ResBaseMsg> signUp(@RequestBody @Valid ReqSignUp req) {
        return ApiResult.successResponse(authService.signUp(req));
    }

    @GetMapping( "/activate/{email}")
    public ApiResult<ResBaseMsg> verificationEmail(@PathVariable String email) {
        return ApiResult.successResponse(authService.verifyEmail(email));
    }

    @PostMapping("/sign-in")
    ApiResult<ResSignIn> signIn(@Valid @RequestBody ReqSignIn req) {
        return ApiResult.successResponse(authService.signIn(req));
    }

    @GetMapping("/token-refresh")
    ApiResult<TokenDto> refreshToken(@Valid @RequestBody ReqRefreshToken req) {
        return ApiResult.successResponse(authService.refreshToken(req));
    }
}
