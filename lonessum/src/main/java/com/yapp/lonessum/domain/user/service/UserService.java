package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.*;
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
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public LoginResponse login(KakaoTokenResponse token) {
        KakaoTokenInfoResponse tokenInfo = kakaoApiClient.getTokenInfo("Bearer " + token.getAccess_token());
        long kakaoServerId = tokenInfo.getId();
        Optional<UserEntity> user = userRepository.findByKakaoServerId(kakaoServerId);
        if (user.isEmpty()) {
            UserEntity newUser = userRepository.save(UserEntity.builder()
                    .kakaoServerId(kakaoServerId)
                    .kakaoAccessToken(token.getAccess_token())
                    .isAuthenticated(false)
                    .isAdult(false)
                    .build());
            return new LoginResponse(jwtService.createAccessToken(newUser.getId()));
        }
        else {
            user.get().changeKakaoAccessToken(token.getAccess_token());
            return new LoginResponse(jwtService.createAccessToken(user.get().getId()));
        }
    }

    @Transactional(readOnly = true)
    public UserEntity getUserFromToken(String token) {
        KakaoTokenInfoResponse tokenInfo = kakaoApiClient.getTokenInfo("Bearer " + token);
        long kakaoServerId = tokenInfo.getId();
        return userRepository.findByKakaoServerId(kakaoServerId).get();
    }

    @Transactional
    public void checkAdult(String token, Boolean isAdult) {
        UserEntity user = getUserFromToken(token);
        user.changeIsAdult(isAdult);
    }

    @Transactional
    public Long testJoin(JoinRequest joinRequest) {
        return userRepository.save(UserEntity.builder()
                .userName(joinRequest.getUserName())
                .password(joinRequest.getPassword())
                .isAdult(true)
                .isAuthenticated(true)
                .build()).getId();
    }

    @Transactional
    public LoginResponse testLogin(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));
        if (userEntity.getPassword().equals(loginRequest.getPassword())) {
            String accessToken = jwtService.createAccessToken(userEntity.getId());
            return new LoginResponse(accessToken);
        }
        else {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }
    }
}
