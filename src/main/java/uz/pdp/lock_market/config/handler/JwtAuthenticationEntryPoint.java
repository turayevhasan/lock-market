package uz.pdp.lock_market.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ErrorResponse;

import java.io.IOException;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper jacksonObjectMapper;
    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        log.error("Responding with 401 unauthorized error. URL -  {}, Message - {}", request.getRequestURI(), ex.getMessage());

        String lang = request.getHeader("Accept-Language");
        String error = messageSource.getMessage("unauthorized", null, Locale.forLanguageTag(lang));

        ApiResult<ErrorResponse> errorDataApiResult = ApiResult.errorResponse(error, 401);
        response.getWriter().write(jacksonObjectMapper.writeValueAsString(errorDataApiResult));

        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
