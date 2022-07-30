package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.*;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.university.UniversityRepository;
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
    private final UniversityRepository universityRepository;

    @Transactional
    public LoginResponse login(KakaoTokenResponse token) {
        KakaoTokenInfoResponse tokenInfo = kakaoApiClient.getTokenInfo("Bearer " + token.getAccess_token());
        long kakaoServerId = tokenInfo.getId();
        Optional<UserEntity> user = userRepository.findByKakaoServerId(kakaoServerId);
        if (user.isEmpty()) {
            UserEntity newUser = userRepository.save(UserEntity.builder()
                    .kakaoServerId(kakaoServerId)
                    .isAuthenticated(false)
                    .isAdult(false)
                    .build());
            return LoginResponse.builder()
                    .accessToken(jwtService.createAccessToken(newUser.getId()))
                    .isAuthenticated(newUser.getIsAuthenticated())
                    .isAdult(newUser.getIsAdult())
                    .build();
        }
        else {
            return LoginResponse.builder()
                    .accessToken(jwtService.createAccessToken(user.get().getId()))
                    .isAuthenticated(user.get().getIsAuthenticated())
                    .isAdult(user.get().getIsAdult())
                    .build();
        }
    }

    @Transactional
    public Long testJoin(JoinRequest joinRequest) {
//        UniversityEntity university = new UniversityEntity();
//        university.setName("Seoul");
//        university.setDomain("snu.ac.kr");
//        universityRepository.save(university);
        return userRepository.save(UserEntity.builder()
                .userName(joinRequest.getUserName())
                .password(joinRequest.getPassword())
                .isAdult(true)
                .isAuthenticated(true)
//                .university(university)
                .build()).getId();
    }

    @Transactional
    public LoginResponse testLogin(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));
        if (userEntity.getPassword().equals(loginRequest.getPassword())) {
            String accessToken = jwtService.createAccessToken(userEntity.getId());
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .isAuthenticated(userEntity.getIsAuthenticated())
                    .isAdult(userEntity.getIsAdult())
                    .build();
        }
        else {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }
    }
}
