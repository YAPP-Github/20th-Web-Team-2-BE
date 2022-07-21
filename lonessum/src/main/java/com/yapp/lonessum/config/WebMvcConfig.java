package com.yapp.lonessum.config;

import com.yapp.lonessum.config.jwt.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    private static final String[] EXCLUDE_PATHS = {
            "/join",
            "/login",
            "/oauth/**",
            "/admin/**",
            "/error",
            "/swagger-ui",
            "/h2-console",
            "/health"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Content-Type")
                .exposedHeaders("Content-Disposition", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(false).maxAge(3600);
    }
}
