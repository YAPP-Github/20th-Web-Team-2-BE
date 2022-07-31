package com.yapp.lonessum.domain.payment.service;

import com.yapp.lonessum.domain.payment.entity.PayName;
import com.yapp.lonessum.domain.payment.repository.PayNameCountRepository;
import com.yapp.lonessum.domain.payment.repository.PayNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayNameService {

    private final PayNameRepository payNameRepository;
    private final PayNameCountRepository payNameCountRepository;

    @Transactional
    public String issuePayName() {
        Long payNameCounter = payNameCountRepository.getPayNameCounter();
        PayName payName = payNameRepository.findByPayNameId(payNameCounter);
        payName.setIsUsing(true);
        payNameRepository.updatePayName(payNameCounter, payName);
        payNameCounter += 1;
        if (payNameCounter >= 300L) {
            payNameCounter = 1L;
        }
        payNameCountRepository.setPayNameCounter(payNameCounter);
        return payName.getPayName();
    }
}
