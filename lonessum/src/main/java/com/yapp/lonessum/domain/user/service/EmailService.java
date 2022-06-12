package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.entity.EmailTokenEntity;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.EmailTokenRepository;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final String SERVICE_EMAIL = "korea@lonessum.com";
    private final Long MAX_EXPIRE_TIME = 3L;

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;
    private final EmailTokenRepository emailTokenRepository;

    @Async
    @Transactional
    public void sendEmail(Long userId, String email) {
        if (!isValidEmail(email)) {
            throw new RuntimeException("이메일 형식이 올바르지 않습니다.");
        }

        //emailToken 생성
        EmailTokenEntity emailToken = EmailTokenEntity.builder()
                .authCode(UUID.randomUUID().toString())
                .expireDate(LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME))
                .build();
        emailTokenRepository.save(emailToken);

        //유저의 emailToken 등록
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        user.issueEmailToken(emailToken);

        //이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SERVICE_EMAIL);
        message.setTo(email);
        message.setSubject("외딴썸 이메일 인증코드입니다.");
        message.setText("다음 인증코드를 입력해주세요.\n"+emailToken.getAuthCode());

        javaMailSender.send(message);
    }

    private static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
}
