package com.isloand.userservice.client;


import feign.form.FormEncoder;
import org.springframework.context.annotation.Bean;


public class KakaoFeignConfig {
    class CustomConfig {
        @Bean
        FormEncoder formEncoder() {
            return new feign.form.FormEncoder();
        }
    }
}
