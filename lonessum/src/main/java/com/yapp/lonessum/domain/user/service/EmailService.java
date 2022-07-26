package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.dto.TestEmailRequest;
import com.yapp.lonessum.domain.user.entity.EmailTokenEntity;
import com.yapp.lonessum.domain.user.entity.UniversityEntity;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.EmailTokenRepository;
import com.yapp.lonessum.domain.user.repository.UniversityRepository;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.utils.AuthCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final String SERVICE_EMAIL = "korea@lonessum.com";
    private final Long MAX_EXPIRE_TIME = 3L;

    private final AuthCodeGenerator authCodeGenerator;
    private final JavaMailSender javaMailSender;
    private final UniversityService universityService;
    private final EmailTokenRepository emailTokenRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public LocalDateTime updateAndSendEmail(UserEntity user, String email) {
        // 유저 대학 이메일 정보 등록
        updateUniversityEmail(user, email);

        //emailToken 생성
        EmailTokenEntity emailToken = EmailTokenEntity.builder()
                .authCode(authCodeGenerator.executeGenerate())
                .expireDate(LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME))
                .build();
        emailTokenRepository.save(emailToken);

        //유저의 emailToken 등록
        user.issueEmailToken(emailToken);

        //이메일 전송
        sendAuthCode(email, emailToken.getAuthCode());

        return emailToken.getExpireDate();
    }

    @Async
    @Transactional
    public void sendAuthCode(String email, String authCode) {
        //이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SERVICE_EMAIL);
        message.setTo(email);
        message.setSubject("외딴썸 이메일 인증코드입니다.");
        message.setText("다음 인증코드를 입력해주세요.\n"+authCode);

        javaMailSender.send(message);
    }

    @Async
    @Transactional
    public void sendMatchResult(String email) {
        //이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SERVICE_EMAIL);
        message.setTo(email);
        message.setSubject("[외딴썸] 매칭이 성사되었습니다.");
        message.setText("지금 매칭 결과를 확인해보세요!.\n");
        message.setText("https://www.lonessum.com\n");

        javaMailSender.send(message);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return Pattern.matches(regex, email);
    }

    @Transactional
    public void updateUniversityEmail(UserEntity user, String email) {
        if (!isValidEmail(email)) {
            throw new RestApiException(UserErrorCode.INVALID_EMAIL);
        }
        // 이메일 발송 테스트를 위해서 주석 처리
        if (!universityService.isSupportedUniversity(email)) {
            throw new RestApiException(UserErrorCode.UNSUPPORTED_EMAIL);
        }
        user.registerUniversityEmail(email);
    }

    @Transactional
    public boolean authenticateWithEmail(UserEntity user, String authCode) {
        EmailTokenEntity emailToken = user.getEmailToken();

        String email = user.getUniversityEmail();
        int idx = email.indexOf("@");
        String domain = email.substring(idx+1);
        UniversityEntity university = universityRepository.findByDomain(domain);

        if (authCode.equals(emailToken.getAuthCode())) {
            if (LocalDateTime.now().isBefore(emailToken.getExpireDate())) {
                user.authenticatedWithEmail(university);
            }
            else {
                // 유효시간 초과 -> 이메일 재전송
                throw new RestApiException(UserErrorCode.EXPIRED_AUTHCODE);
            }
        }
        else {
            // 잘못된 인증코드 -> 재입력 시도
            throw new RestApiException(UserErrorCode.INVALID_AUTHCODE);
        }
        return user.getIsAuthenticated();
    }

    public void addTestEmail(TestEmailRequest testEmailRequest) {
        UniversityEntity university = new UniversityEntity();
        university.setName(testEmailRequest.getName());
        university.setDomain(testEmailRequest.getDomain());
        universityRepository.save(university);
    }
}
