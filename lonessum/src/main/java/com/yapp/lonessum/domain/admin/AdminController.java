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
    public ResponseEntity<List<UserStatusDto>> setUserMeetingPayment(@RequestBody PaymentDto paymentDto) {
        adminService.setUserMeetingPayment(paymentDto);
        return ResponseEntity.ok(adminService.getUserDatingStatusList());
    }

    @PatchMapping("/users/dating/payment")
    public ResponseEntity<List<UserStatusDto>> setUserDatingPayment(@RequestBody PaymentDto paymentDto) {
        adminService.setUserDatingPayment(paymentDto);
        return ResponseEntity.ok(adminService.getUserDatingStatusList());
    }
}
