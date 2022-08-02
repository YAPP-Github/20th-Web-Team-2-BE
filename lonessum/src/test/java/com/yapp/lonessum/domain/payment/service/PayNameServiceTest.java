package com.yapp.lonessum.domain.payment.service;

import com.yapp.lonessum.domain.admin.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PayNameServiceTest {
    @Autowired
    PayNameService payNameService;

    @Test
    public void redis_counter가_정상적으로_동작한다() {
        //given when
        String payName1 = payNameService.getPayName();
        String payName2 = payNameService.getPayName();

        //then
        System.out.println(payName1);
        System.out.println(payName2);
        Assertions.assertNotEquals(payName1, payName2);
    }
}
