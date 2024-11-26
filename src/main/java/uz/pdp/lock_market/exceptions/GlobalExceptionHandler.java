package uz.pdp.lock_market.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ErrorResponse;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;


@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    // User errors
    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<ApiResult<ErrorResponse>> handleException(RestException ex, HttpServletRequest request) {
        ErrorTypeEnum errorTypeEnum = ex.getErrorTypeEnum();
        String error = messageSource.getMessage(errorTypeEnum.getKey(), null, request.getLocale());

        ErrorResponse errorData = new ErrorResponse(error, errorTypeEnum.getStatus().value());
        return new ResponseEntity<>(ApiResult.errorResponse(errorData), ex.getStatus());
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException -> ", ex);
        String errorFieldMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> String.format("%s: %s, ", getJsonPropertyName(fe, ex.getTarget()), fe.getDefaultMessage()))
                .distinct()
                .reduce("", String::concat);
        ObjectError objectError = ex.getBindingResult().getGlobalError();
        String errorMessage = Objects.nonNull(objectError) ? objectError.getDefaultMessage() : "Invalid request parameters";
        if (StringUtils.hasText(errorFieldMessage)) {
            assert errorMessage != null;
            errorMessage = errorMessage.concat(" (").concat(errorFieldMessage).concat(")");
        }
        return new ResponseEntity<>(ApiResult.errorResponse(errorMessage, 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<?> handleException(TypeMismatchException ex) {
        log.error("TypeMismatchException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<?> handleException(ServletRequestBindingException ex) {
        log.error("ServletRequestBindingException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestPartException.class})
    public ResponseEntity<?> handleException(MissingServletRequestPartException ex) {
        log.error("MissingServletRequestPartException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<?> handleException(BindException ex) {
        log.error("BindException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleException(AccessDeniedException ex,  @RequestHeader("Accept-Language") String lang) {
        log.error("AccessDeniedException -> ", ex);
        String error = messageSource.getMessage("dont.have.perm", null, Locale.forLanguageTag(lang));
        return new ResponseEntity<>(
                ApiResult.errorResponse(error, 400),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<?> handleException(MissingPathVariableException ex, HttpServletRequest request) {
        log.error("MissingPathVariableException -> ", ex);
        String error = messageSource.getMessage("page.not.found", null, request.getLocale());
        return new ResponseEntity<>(
                ApiResult.errorResponse(error, 404), //ex.me
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }


    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException ex,  HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException -> ", ex);
        String error = messageSource.getMessage("method.error", null, request.getLocale());
        return new ResponseEntity<>(
                ApiResult.errorResponse(error, 405),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        log.error("HttpMediaTypeNotAcceptableException -> ", ex);

        String error = messageSource.getMessage("accept.error", null, request.getLocale());
        return new ResponseEntity<>(
                ApiResult.errorResponse(error, 406),
                HttpStatus.NOT_ACCEPTABLE);
    }


    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.error("HttpMediaTypeNotSupportedException -> ", ex);
        String error = messageSource.getMessage("media.type.error", null, request.getLocale());
        return new ResponseEntity<>(
                ApiResult.errorResponse(error, 415),
                HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(value = {ConversionNotSupportedException.class})
    public ResponseEntity<?> handleException(ConversionNotSupportedException ex) {
        log.error("ConversionNotSupportedException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {HttpMessageNotWritableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotWritableException ex) {
        log.error("HttpMessageNotWritableException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Exception -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public ResponseEntity<?> handleException(AsyncRequestTimeoutException ex) {
        log.error("AsyncRequestTimeoutException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 503),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    private String getJsonPropertyName(@NonNull FieldError fieldError, @Nullable Object target) {
        String fieldName = fieldError.getField();
        if (target == null) {
            return fieldName;
        }
        JsonProperty jsonPropertyAnnotation = Arrays.stream(target.getClass()
                        .getDeclaredFields())
                .filter(field -> field.getName().equals(fieldName))
                .findFirst()
                .map(field -> field.getAnnotation(JsonProperty.class))
                .orElse(null);

        return (jsonPropertyAnnotation != null) ? jsonPropertyAnnotation.value() : fieldName;
    }

}
