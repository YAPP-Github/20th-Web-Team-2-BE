package com.yapp.lonessum.domain.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlackListServiceTest {
    @Autowired BlackListService blackListService;

    @Test
    public void 탈퇴시_블랙리스트에_등록한다() {
        //given
        blackListService.registerBlackList("testId", "testJwt");

        //when
        Boolean result = blackListService.isJwtInBlackList("testJwt");

        //then
        Assertions.assertEquals(result, true);
    }
}
