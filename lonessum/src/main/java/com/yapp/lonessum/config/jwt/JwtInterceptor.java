package com.yapp.lonessum.config.jwt;

import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("url = " + request.getRequestURI());
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token != null && token.length() > 0) {
            return jwtService.isValid(token);
        } else {
            throw new RestApiException(UserErrorCode.JWT_NOT_EXIST);
        }
    }
}
