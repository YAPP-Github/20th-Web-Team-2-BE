package com.yapp.lonessum.domain.user.client;


import feign.form.FormEncoder;
import org.springframework.context.annotation.Bean;


public class KakaoFeignConfig {
    class CustomConfig {
        @Bean
        FormEncoder formEncoder() {
            return new FormEncoder();
        }
    }
}
