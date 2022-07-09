package com.yapp.lonessum.domain.payment.client;

import com.yapp.lonessum.domain.payment.dto.PayplePartnerAuthRequest;
import com.yapp.lonessum.domain.payment.dto.PayplePartnerAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "payple-partner-auth",
        url = "https://democpay.payple.kr/php/auth.php",
        configuration = PaypleFeignConfig.class
)
public interface PayplePartnerAuthClient {
    @PostMapping
    PayplePartnerAuthResponse getPartnerAuth(PayplePartnerAuthRequest payplePartnerAuthRequest);
}
