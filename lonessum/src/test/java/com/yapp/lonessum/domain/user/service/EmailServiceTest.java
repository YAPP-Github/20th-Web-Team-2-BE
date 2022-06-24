package com.yapp.lonessum.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

class EmailServiceTest {

    @Test
    void 유효한_이메일1() {
        // given
        String email = "lonessum@gmail.com";

        // when
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        boolean result = Pattern.matches(regex, email);

        // then
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void 유효한_이메일2() {
        // given
        String email = "lonessum77@gmail.com";

        // when
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        boolean result = Pattern.matches(regex, email);

        // then
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void 유효하지_않은_이메일1() {
        // given
        String email = "lonessum@gmail..com";

        // when
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        boolean result = Pattern.matches(regex, email);

        // then
        Assertions.assertThat(result).isEqualTo(false);
    }

    @Test
    void 유효하지_않은_이메일2() {
        // given
        String email = "lone?ssum@gma-il..com";

        // when
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        boolean result = Pattern.matches(regex, email);

        // then
        Assertions.assertThat(result).isEqualTo(false);
    }
}