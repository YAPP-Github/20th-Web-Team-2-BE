package com.yapp.lonessum.domain.payment.service;

import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.payment.client.PayplePartnerAuthClient;
import com.yapp.lonessum.domain.payment.client.PayplePaymentCertClient;
import com.yapp.lonessum.domain.payment.dto.PayplePartnerAuthRequest;
import com.yapp.lonessum.domain.payment.dto.PayplePartnerAuthResponse;
import com.yapp.lonessum.domain.payment.dto.PayplePaymentCertRequest;
import com.yapp.lonessum.domain.payment.dto.PayplePaymentCertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayplePartnerAuthClient payplePartnerAuthClient;
    private final PayplePaymentCertClient payplePaymentCertClient;

    public String generatePayName() {
        return null;
    }

    public PayplePartnerAuthResponse getPartnerAuth() {
        PayplePartnerAuthRequest payplePartnerAuthRequest = new PayplePartnerAuthRequest();
        payplePartnerAuthRequest.setCst_id("test");
        payplePartnerAuthRequest.setCustKey("abcd1234567890");
        return payplePartnerAuthClient.getPartnerAuth(payplePartnerAuthRequest);
    }

    public PayplePaymentCertResponse certifyPayment() {
        PayplePaymentCertRequest payplePaymentCertRequest = new PayplePaymentCertRequest();
        return payplePaymentCertClient.certifyPayment(payplePaymentCertRequest);
    }

    public Object payMeeting(MeetingSurveyEntity meetingSurvey) {
        return null;
    }

    public Object payDating(DatingSurveyEntity datingSurvey) {
        return null;
    }
}
