package com.yapp.lonessum.domain.payment.client;

import feign.form.FormEncoder;
import org.springframework.context.annotation.Bean;

public class PaypleFeignConfig {
    class CustomConfig {
        @Bean
        FormEncoder formEncoder() {
            return new FormEncoder();
        }
    }
}
