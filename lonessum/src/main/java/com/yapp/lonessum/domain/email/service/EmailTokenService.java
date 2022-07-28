package com.yapp.lonessum.domain.email.service;

import com.yapp.lonessum.domain.email.entity.EmailToken;
import com.yapp.lonessum.domain.email.repository.EmailTokenRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.utils.AuthCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTokenService {

    private final AuthCodeGenerator authCodeGenerator;
    private final EmailTokenRepository emailTokenRepository;

    public String issueEmailToken(Long userId) {
        String authCode = authCodeGenerator.executeGenerate();
        emailTokenRepository.save(EmailToken.builder()
                .userId(userId)
                .authCode(authCode)
                .build());
        return authCode;
    }

    public Boolean isValidAuthCode(UserEntity user, String authCode) {
        EmailToken emailToken = emailTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RestApiException(UserErrorCode.EXPIRED_AUTHCODE));
        if (authCode.equals(emailToken.getAuthCode())) {
            return true;
        }
        else {
            // 잘못된 인증코드 -> 재입력 시도
            throw new RestApiException(UserErrorCode.INVALID_AUTHCODE);
        }
    }
}
