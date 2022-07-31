package com.yapp.lonessum.domain.payment.service;

import com.yapp.lonessum.domain.payment.entity.PayName;
import com.yapp.lonessum.domain.payment.repository.PayNameCountRepository;
import com.yapp.lonessum.domain.payment.repository.PayNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayNameService {

    private final PayNameRepository payNameRepository;
    private final PayNameCountRepository payNameCountRepository;

    public String issuePayName() {
        Long payNameCounter = payNameCountRepository.getPayNameCounter();
        PayName payName = payNameRepository.findByPayNameId(payNameCounter);
        payName.setIsUsing(true);
        payNameRepository.updatePayName(payNameCounter, payName);
        payNameCountRepository.setPayNameCounter(payNameCounter+1);
        return payName.getPayName();
    }
}
