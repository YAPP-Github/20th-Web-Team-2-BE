package com.yapp.lonessum.domain.payment.client;

import com.yapp.lonessum.domain.payment.dto.PayplePaymentCertRequest;
import com.yapp.lonessum.domain.payment.dto.PayplePaymentCertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "payple-payment-cert",
        url = "https://democpay.payple.kr/php/PayCardConfirmAct.php?ACT_=PAYM",
        configuration = PaypleFeignConfig.class
)
public interface PayplePaymentCertClient {
    @PostMapping
    PayplePaymentCertResponse certifyPayment(PayplePaymentCertRequest payplePaymentCertRequest);
}
