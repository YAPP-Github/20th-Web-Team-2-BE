package com.yapp.lonessum.domain.payment.service;

import com.yapp.lonessum.domain.payment.entity.PayName;
import com.yapp.lonessum.domain.payment.repository.PayNameCountRepository;
import com.yapp.lonessum.domain.payment.repository.PayNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class PayNameService {

    private final PayNameRepository payNameRepository;
    private final PayNameCountRepository payNameCountRepository;

    @Value("${payname.resource.path}")
    private String payNameResourcePath;
    private List<String> payNameList = new ArrayList<>();

    @PostConstruct
    public void setPayNameListData() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(payNameResourcePath);

        Scanner scanner = new Scanner(fileInputStream);
        while(scanner.hasNextLine()) {
            payNameList.add(scanner.nextLine());
        }
    }

    @Transactional
    public String getPayName() {
        int payNameIdx = payNameCountRepository.countWithPreValue();

        return payNameList.get(payNameIdx);
    }

    public void print() {
        for(String s : payNameList) {
            System.out.println("s = " + s);
        }
    }

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
