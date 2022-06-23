package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.dto.AuthCodeResponse;
import com.yapp.lonessum.domain.user.entity.EmailTokenEntity;
import com.yapp.lonessum.domain.user.entity.UniversityEntity;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UniversityRepository;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmailService emailService;
    private final UniversityService universityService;

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public void updateUniversityEmail(Long userId, String email) {
        if (!emailService.isValidEmail(email)) {
            throw new RuntimeException("이메일 형식이 올바르지 않습니다.");
        }

        if (!universityService.isSupportedUniversity(email)) {
            throw new RuntimeException("지원하지 않는 대학입니다.");
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        user.registerUniversityEmail(email);

        //테스트용
//        UserEntity user = UserEntity.builder()
//                .kakaoEmail("hi@kakao.com")
//                .isAuthenticated(false)
//                .build();

        user.registerUniversityEmail(email);
        userRepository.save(user);
    }

    @Transactional
    public AuthCodeResponse authenticateWithEmail(Long userId, String authCode) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        EmailTokenEntity emailToken = user.getEmailToken();

        String email = user.getUniversityEmail();
        int idx = email.indexOf("@");
        String domain = email.substring(idx+1);
        UniversityEntity university = universityRepository.findByDomain(domain);

        if (authCode.equals(emailToken.getAuthCode())) {
            if (LocalDateTime.now().isBefore(emailToken.getExpireDate())) {
                user.authenticatedWithEmail(university);
                return AuthCodeResponse.builder()
                        .message("인증 성공.")
                        .build();
            }
            else {
                // 유효시간 초과 -> 이메일 재전송
                return AuthCodeResponse.builder()
                        .message("인증코드의 유효기간이 만료되었습니다. 이메일 전송을 다시 요청해주세요.")
                        .build();
            }
        }
        else {
            // 잘못된 인증코드 -> 재입력 시도
            return AuthCodeResponse.builder()
                    .message("인증코드가 일치하지 않습니다. 인증코드를 다시 입력해주세요.")
                    .build();
        }
    }
}
