package com.yapp.lonessum.domain.email.service;

import com.yapp.lonessum.domain.email.dto.TestEmailRequest;
import com.yapp.lonessum.domain.university.UniversityEntity;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.university.UniversityRepository;
import com.yapp.lonessum.domain.university.UniversityService;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final String SERVICE_EMAIL = "korea@lonessum.com";

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    private final EmailTokenService emailTokenService;
    private final UniversityService universityService;

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public LocalDateTime updateAndSendEmail(UserEntity user, String email) throws MessagingException {
        // 유저 대학 이메일 정보 등록
        updateUniversityEmail(user, email);
        
        // 인증코드 생성 후 Redis 저장
        String authCode = emailTokenService.issueEmailToken(user.getId());

        //이메일 전송
        sendAuthCode(email, authCode);

        return LocalDateTime.now();
    }

    @Async
    @Transactional
    public void sendAuthCode(String email, String authCode) throws MessagingException {
        //이메일 메시지 생성
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress(SERVICE_EMAIL));
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[외딴썸] 인증코드를 확인해주세요.");
        message.setText(setAuthCodeContext(authCode), "utf-8", "html");

        javaMailSender.send(message);
    }

    @Async
    @Transactional
    public void sendMatchResult(String email) throws MessagingException {
        //이메일 메시지 생성
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress(SERVICE_EMAIL));
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[외딴썸] 매칭결과를 확인해주세요.");
        message.setText(setMatchResultContext(), "utf-8", "html");

        javaMailSender.send(message);
    }

    private String setAuthCodeContext(String code) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
        return templateEngine.process("AuthCode", context); // AuthCode.html
    }

    private String setMatchResultContext() { // 타임리프 설정하는 코드
        Context context = new Context();
        return templateEngine.process("MatchResult", context); // MatchResult.html
    }

    public boolean isValidEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return Pattern.matches(regex, email);
    }

    public void updateUniversityEmail(UserEntity user, String email) {
        if (!isValidEmail(email)) {
            throw new RestApiException(UserErrorCode.INVALID_EMAIL);
        }
        // 이메일 발송 테스트를 위해서 주석 처리
        if (!universityService.isSupportedUniversity(email)) {
            throw new RestApiException(UserErrorCode.UNSUPPORTED_EMAIL);
        }
        user.registerUniversityEmail(email);
        userRepository.save(user);
    }

    @Transactional
    public boolean authenticateWithEmail(UserEntity user, String authCode) {
        if (emailTokenService.isValidAuthCode(user, authCode)) {
            String email = user.getUniversityEmail();
            int idx = email.indexOf("@");
            String domain = email.substring(idx+1);
            UniversityEntity university = universityRepository.findByDomain(domain);
            user.authenticatedWithEmail(university);
            return true;
        }
        return false;
    }

    public void addTestEmail(TestEmailRequest testEmailRequest) {
        UniversityEntity university = new UniversityEntity();
        university.setName(testEmailRequest.getName());
        university.setDomain(testEmailRequest.getDomain());
        universityRepository.save(university);
    }
}
