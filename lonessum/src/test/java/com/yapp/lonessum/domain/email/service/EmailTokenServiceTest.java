package com.yapp.lonessum.domain.email.service;

import com.yapp.lonessum.domain.email.entity.EmailToken;
import com.yapp.lonessum.domain.email.repository.EmailTokenRepository;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmailTokenServiceTest {

    @Autowired
    EmailTokenService emailTokenService;
    @Autowired
    EmailTokenRepository emailTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 이메일_토큰이_발급된다() throws InterruptedException {
        //given
        String authCode = emailTokenService.issueEmailToken(123L);

        //when
        EmailToken result = emailTokenRepository.findById("123").get();

        //then
        Assertions.assertThat(authCode).isEqualTo(result.getAuthCode());
        System.out.println("result.getAuthCode() = " + result.getAuthCode());
        System.out.println("result.getUserId() = " + result.getUserId());

        Thread.sleep(4000);
        EmailToken result2 = emailTokenRepository.findById("123").get();
        Assertions.assertThat(result2).isNull();

    }

}