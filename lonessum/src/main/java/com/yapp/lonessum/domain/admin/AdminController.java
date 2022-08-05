package com.yapp.lonessum.domain.admin;

import com.yapp.lonessum.domain.abroadArea.AbroadAreaEntity;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaRepository;
import com.yapp.lonessum.domain.university.UniversityEntity;
import com.yapp.lonessum.domain.university.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UniversityRepository universityRepository;
    private final AbroadAreaRepository abroadAreaRepository;

    @GetMapping("/meeting/payment-targets")
    public ResponseEntity<List<PaymentTargetDto>> getMeetingPaymentTargets() {
        return ResponseEntity.ok(adminService.getMeetingPaymentTargetList());
    }

    @GetMapping("/dating/payment-targets")
    public ResponseEntity<List<PaymentTargetDto>> getDatingPaymentTargets() {
        return ResponseEntity.ok(adminService.getDatingPaymentTargetList());
    }

    @PatchMapping("/users/meeting/payment")
    public ResponseEntity<PaymentStatusDto> setUserMeetingPayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(adminService.setUserMeetingPayment(paymentDto));
    }

    @PatchMapping("/users/dating/payment")
    public ResponseEntity<PaymentStatusDto> setUserDatingPayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(adminService.setUserDatingPayment(paymentDto));
    }

    @GetMapping("/meeting/refund-targets")
    public ResponseEntity<List<RefundTargetDto>> getMeetingRefundTargets() {
        return ResponseEntity.ok(adminService.getMeetingRefundTargets());
    }

    @GetMapping("/dating/refund-refund")
    public ResponseEntity<List<RefundTargetDto>> getDatingRefundTargets() {
        return ResponseEntity.ok(adminService.getDatingRefundTargets());
    }

    @PatchMapping("/users/meeting/refund")
    public ResponseEntity<PaymentStatusDto> setUserMeetingRefund(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(adminService.setUserMeetingRefund(paymentDto));
    }

    @PatchMapping("/users/dating/refund")
    public ResponseEntity<PaymentStatusDto> setUserDatingRefund(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(adminService.setUserDatingRefund(paymentDto));
    }

    @PostMapping("/universities")
    public ResponseEntity<UniversityDto> registerUniversityInfo(@RequestBody UniversityDto universityDto) {
        UniversityEntity universityEntity = new UniversityEntity();
        universityEntity.setDomain(universityDto.getDomain());
        universityEntity.setName(universityDto.getName());

        universityRepository.save(universityEntity);

        return ResponseEntity.ok(new UniversityDto(universityEntity.getName(), universityEntity.getDomain()));
    }

    @PostMapping("/areas")
    public ResponseEntity<AbroadAreaDto> registerAreaInfo(@RequestBody AbroadAreaDto abroadAreaDto) {
        AbroadAreaEntity abroadAreaEntity = new AbroadAreaEntity();
        abroadAreaEntity.setName(abroadAreaDto.getName());

        abroadAreaRepository.save(abroadAreaEntity);

        return ResponseEntity.ok(new AbroadAreaDto(abroadAreaEntity.getName()));
    }
}
