package uz.pdp.lock_market.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ErrorResponse;

import java.io.IOException;


@Order(value = Integer.MIN_VALUE)
@Component
@Slf4j
@RequiredArgsConstructor
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        log.error("Responding with 403 forbidden error. URL -  {}, Message - {}", request.getRequestURI(), ex.getMessage());

        ApiResult<ErrorResponse> errorDataApiResult = ApiResult.errorResponse("forbidden", 403);
        response.getWriter().write(objectMapper.writeValueAsString(errorDataApiResult));

        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
