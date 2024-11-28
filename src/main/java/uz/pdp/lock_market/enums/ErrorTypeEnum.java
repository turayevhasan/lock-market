package uz.pdp.lock_market.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorTypeEnum {
    ACCESS_TOKEN_NOT_EXPIRED("access.token.expired", HttpStatus.UNAUTHORIZED),
    APPLICATION_NOT_FOUND("application.not.found", HttpStatus.NOT_FOUND),
    ATTACHMENT_NOT_FOUND("file.not.found", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_EN_ALREADY_EXISTS("category.name.en.exists"),
    CATEGORY_NAME_RU_ALREADY_EXISTS("category.name.ru.exists"),
    CATEGORY_NAME_UZ_ALREADY_EXISTS("category.name.uz.exists"),
    CATEGORY_NOT_FOUND("category.not.found", HttpStatus.NOT_FOUND),
    CODE_NOT_FOUND("code.not.found", HttpStatus.NOT_FOUND),
    COMMENTARY_NOT_FOUND("commentary.not.found", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("comment.not.found", HttpStatus.NOT_FOUND),
    CONFIRM_PASSWORD_NOT_MATCH("confirm.password.not.match"),
    EMAIL_ALREADY_EXISTS("email.already.exists"),
    EMAIL_NOT_VALID("email.not.valid"),
    ERROR_SAVING_FILE("error.saving.file"),
    FEATURE_NOT_FOUND("feature.not.found", HttpStatus.NOT_FOUND),
    FILE_CANNOT_DELETED("file.cannot.delete"),
    FILE_NOT_FOUND("file.not.found", HttpStatus.NOT_FOUND),
    FORBIDDEN("forbidden", HttpStatus.FORBIDDEN),
    INPUT_CODE_NOT_MATCH("input.not.match"),
    LOCK_ALREADY_EXISTS("lock.already.exists"),
    LOCK_NOT_FOUND("lock.not.found", HttpStatus.NOT_FOUND),
    LOCK_NAME_EN_ALREADY_EXISTS("lock.name.en.exists"),
    LOCK_NAME_RU_ALREADY_EXISTS("lock.name.ru.exists"),
    LOCK_NAME_UZ_ALREADY_EXISTS("lock.name.uz.exists"),
    LOGIN_OR_PASSWORD_ERROR("login.error", HttpStatus.FORBIDDEN),
    ORDER_ALREADY_IN_THIS_STATUS("order.already.in.this.status"),
    ORDER_ARCHIVED("order.archived"),
    ORDER_NOT_FOUND("order.not.found", HttpStatus.NOT_FOUND),
    PHONE_NUMBER_NOT_VALID("phone.not.valid"),
    PROMOCODE_ALREADY_EXISTS("promo.already.exists"),
    PROMOCODE_IS_NOT_VALID("promo.not.valid"),
    REFRESH_TOKEN_EXPIRED("refresh.token.expired", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND("role.not.found", HttpStatus.NOT_FOUND),
    TOKEN_NOT_VALID("token.not.valid"),
    USER_NOT_ACTIVATED("user.not.activated"),
    USER_NOT_FOUND("user.not.found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_OR_DISABLED("user.not.found.or.disabled", HttpStatus.FORBIDDEN),
    USER_PERMISSION_RESTRICTION("user.permission.restriction", HttpStatus.UNAUTHORIZED),
    WRONG_ACCESS_TOKEN("wrong.access.token", HttpStatus.UNAUTHORIZED),
    PROMOCODE_NOT_FOUND("promo.not.found", HttpStatus.NOT_FOUND),
    LOCK_FEATURE_ALREADY_EXISTS("lock.feature.exists"),
    CONTACT_NOT_FOUND("contact.not.found", HttpStatus.NOT_FOUND),;

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String key;

    ErrorTypeEnum(String key, HttpStatus status) {
        this.key = key;
        this.status = status;
    }

    ErrorTypeEnum(String key) {
        this.key = key;
    }
}
