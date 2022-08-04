package com.yapp.lonessum.domain.abroadArea;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AbroadAreaServiceTest {
    @Autowired
    AbroadAreaRepository abroadAreaRepository;

    @Test
    void getAllAbroadAreas() {
        abroadAreaRepository.findAll().forEach((abroadAreaEntity -> {
            String areaName = abroadAreaEntity.getName();
            System.out.println("abroadAreaEntity.getName() = " + areaName.substring(0, areaName.lastIndexOf(",")));
        }));
    }
}