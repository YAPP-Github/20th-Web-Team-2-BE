package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenInfoResponse;
import com.yapp.lonessum.domain.user.dto.KakaoTokenResponse;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KakaoApiClient kakaoApiClient;
    private final EmailService emailService;
    private final UniversityService universityService;
    private final UserRepository userRepository;

    @Transactional
    public void login(KakaoTokenResponse token) {
        KakaoTokenInfoResponse tokenInfo = kakaoApiClient.getTokenInfo(token.getAccess_token());
        long kakaoServerId = tokenInfo.getId();
        Optional<UserEntity> user = userRepository.findByKakaoServerId(kakaoServerId);
        if (user.isEmpty()) {
            userRepository.save(UserEntity.builder()
                    .kakaoServerId(kakaoServerId)
                    .build());
        }
    }

    @Transactional(readOnly = true)
    public UserEntity getUserFromToken(String token) {
        KakaoTokenInfoResponse tokenInfo = kakaoApiClient.getTokenInfo(token);
        long kakaoServerId = tokenInfo.getId();
        return userRepository.findByKakaoServerId(kakaoServerId).get();
    }

    @Transactional
    public void updateUniversityEmail(UserEntity user, String email) {
        if (!emailService.isValidEmail(email)) {
            throw new RestApiException(UserErrorCode.INVALID_EMAIL);
        }
        if (!universityService.isSupportedUniversity(email)) {
            throw new RestApiException(UserErrorCode.UNSUPPORTED_EMAIL);
        }
        user.registerUniversityEmail(email);
    }
}
