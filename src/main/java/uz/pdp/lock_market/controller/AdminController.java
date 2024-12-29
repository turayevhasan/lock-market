package uz.pdp.lock_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.enums.RoleEnum;
import uz.pdp.lock_market.enums.UserStatus;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.service.AdminService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.ADMIN)
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/change-role")
    public ApiResult<ResBaseMsg> changeRole(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestParam("userId") UUID userId,
            @RequestParam("role") RoleEnum role) {
        return ApiResult.successResponse(adminService.changeUserRole(lang, userId, role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/change-status")
    public ApiResult<ResBaseMsg> changeStatus(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestParam("userId") UUID userId,
            @RequestParam("role") UserStatus status) {
        return ApiResult.successResponse(adminService.changeStatus(lang, userId, status));
    }
}
