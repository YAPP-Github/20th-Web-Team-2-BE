package com.yapp.lonessum.domain.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users/meeting/status")
    public ResponseEntity<List<UserStatusDto>> getUserMeetingStatusList() {
        return ResponseEntity.ok(adminService.getUserMeetingStatusList());
    }

    @GetMapping("/users/dating/status")
    public ResponseEntity<List<UserStatusDto>> getUserDatingStatusList() {
        return ResponseEntity.ok(adminService.getUserDatingStatusList());
    }

    @PatchMapping("/users/meeting/payment")
    public ResponseEntity<PaymentStatusDto> setUserMeetingPayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(adminService.setUserMeetingPayment(paymentDto));
    }

    @PatchMapping("/users/dating/payment")
    public ResponseEntity<PaymentStatusDto> setUserDatingPayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(adminService.setUserDatingPayment(paymentDto));
    }

    @GetMapping("/meeting/payment-targets")
    public ResponseEntity<List<PaymentTargetDto>> getMeetingPaymentTargets() {
        return ResponseEntity.ok(adminService.getMeetingPaymentTargetList());
    }

    @GetMapping("/dating/payment-targets")
    public ResponseEntity<List<PaymentTargetDto>> getDatingPaymentTargets() {
        return ResponseEntity.ok(adminService.getDatingPaymentTargetList());
    }
}
