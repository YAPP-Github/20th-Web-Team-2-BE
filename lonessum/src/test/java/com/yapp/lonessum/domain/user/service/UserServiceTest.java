package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원탈퇴_테스트() {
        //given
        userRepository.save(UserEntity.builder().id(1L).build());

        //when
        userService.withdraw(1L);

        //then
        Assertions.assertThat(userRepository.findById(1L).isEmpty()).isEqualTo(true);
    }
}
