package com.yapp.lonessum.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);
    private static final List<String> ALLOWED_USER = Arrays.asList("minseok");
    private static final String KEY = "lonessum1234";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String key = ServletRequestUtils.getStringParameter(request, "key");
        String user = ServletRequestUtils.getStringParameter(request, "user");

        logger.info("key : {} , user : {}", key, user);
        try {
            isAllowedUser(user);
            isCorrectKey(key);

            filterChain.doFilter(request, response);
        } catch(AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("SORRY, YOU ARE AN UNAUTHORIZED USER!");
        }
    }

    private void isCorrectKey(String key) throws AuthenticationException {
        if(!KEY.equals(key)) {
            throw new AuthenticationException();
        }
    }
    private void isAllowedUser(String user) throws AuthenticationException {
        if(!ALLOWED_USER.contains(user)) {
            throw new AuthenticationException();
        }
    }
}
