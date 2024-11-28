package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.user.req.ProfileUpdateReq;
import uz.pdp.lock_market.payload.user.req.ResetPasswordReq;
import uz.pdp.lock_market.payload.user.req.UpdatePasswordReq;
import uz.pdp.lock_market.payload.user.res.UserRes;
import uz.pdp.lock_market.service.UserService;
import uz.pdp.lock_market.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.USER)
public class UserController {
    private final UserService userService;

    @GetMapping("/forgot-password/{email}")
    public ApiResult<ResBaseMsg> forgotPassword(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("email") String email) {
        return ApiResult.successResponse(userService.forgotPassword(lang, email));
    }

    @PostMapping("/reset-password")
    public ApiResult<ResBaseMsg> resetPassword(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid ResetPasswordReq req) {
        return ApiResult.successResponse(userService.resetPassword(lang, req));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-password")
    public ApiResult<ResBaseMsg> changePassword(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid UpdatePasswordReq req) {
        return ApiResult.successResponse(userService.changePassword(lang, req));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-profile")
    public ApiResult<UserRes> updateProfile(@RequestBody ProfileUpdateReq req) {
        return ApiResult.successResponse(userService.update(req));
    }
}
