package com.yapp.lonessum.domain.payment.controller;

import com.yapp.lonessum.domain.payment.dto.*;
import com.yapp.lonessum.domain.payment.service.PaymentService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final UserService userService;
    private final PaymentService paymentService;

    /*
    * 구매하기 버튼 -> 페이플 서버로 파트너 인증 요청 -> 파트너 인증 결과를 반환
    * 클라이언트로 파트너 인증 결과와 추가 정보(주문id, 유저id 등) 반환
    * */
    @PostMapping
    public ResponseEntity<PayplePartnerAuthResponse> getPartnerAuth() {
        return ResponseEntity.ok(paymentService.getPartnerAuth());
    }

    /*
     * 클라이언트에서 페이플 결제창 호출 후 결제 진행 -> 페이플 서버에서 외딴썸 서버로 결제 정보를 리다이렉트
     * */
    @PostMapping("/result")
    public ResponseEntity<PayplePaymentResultResponse> receivePaymentResult(PayplePaymentResultResponse payplePaymentResultResponse) {
        return ResponseEntity.ok(payplePaymentResultResponse);
    }

    /*
    * 페이플 서버가 보낸 결제 정보를 가지고 결제 요청 재컨펌 (최종 승인)
    * */
    public ResponseEntity<PayplePaymentCertResponse> certifyPayment() {
        return ResponseEntity.ok(paymentService.certifyPayment());
    }

    @PostMapping("/meeting")
    public ResponseEntity payMeeting(@RequestHeader(value = "Authorization") String token) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(paymentService.payMeeting(user.getMeetingSurvey()));
    }

    @PostMapping("/dating")
    public ResponseEntity payDating(@RequestHeader(value = "Authorization") String token) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(paymentService.payDating(user.getDatingSurvey()));
    }
}
