package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.university.UniversityEntity;
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

import java.util.NoSuchElementException;
import java.util.Objects;
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
        } else {
            return LoginResponse.builder()
                    .accessToken(jwtService.createAccessToken(user.get().getId()))
                    .isAuthenticated(user.get().getIsAuthenticated())
                    .isAdult(user.get().getIsAdult())
                    .build();
        }
    }

    @Transactional
    public void withdraw(Long userId) {
        UserEntity withdrawUser = userRepository.findUserWithSurveys(userId)
                .orElseThrow(() -> new NoSuchElementException("유저 정보가 존재하지 않습니다."));


        MeetingSurveyEntity meetingSurvey = withdrawUser.getMeetingSurvey();
        DatingSurveyEntity datingSurvey = withdrawUser.getDatingSurvey();

        //meeting dating 매칭된 것 없는 case
        if (Objects.isNull(meetingSurvey) && Objects.isNull(datingSurvey)) {
            userRepository.deleteById(userId);
            return;
        }

        //미팅 case
        if (!Objects.isNull(meetingSurvey)) {
            if (meetingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
                // 남자 탈퇴, 입금 전 -> 여자 실패
                if (meetingSurvey.getGender() == Gender.MALE) {
                    meetingSurvey.getMeetingMatching().getFemaleSurvey().changeMatchStatus(MatchStatus.FAILED);
                }
            }
            else if (meetingSurvey.getMatchStatus() == MatchStatus.PAID) {
                if (meetingSurvey.getGender() == Gender.FEMALE) {
                    // 여자 탈퇴, 입금 후 -> 남자 환불(NEED_REFUND)
                    if (meetingSurvey.getMeetingMatching().getPayment().getIsPaid() == true) {
                        meetingSurvey.getMeetingMatching().getPayment().updateNeedRefundStatus(true);
                        meetingSurvey.getMeetingMatching().getMaleSurvey().changeMatchStatus(MatchStatus.CANCELED_OR_NEED_REFUND);
                    }
                    // 여자 탈퇴, 입금 전 -> 남자 취소(CANCELED)
                    else {
                        meetingSurvey.getMeetingMatching().getMaleSurvey().changeMatchStatus(MatchStatus.CANCELED_OR_NEED_REFUND);
                    }
                }
                else {
                    // 남자 탈퇴, 입금 후 -> 그대로
                }
            }
        }

        //dating case
        if (!Objects.isNull(datingSurvey)) {
            if (datingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
                // 남자 탈퇴, 입금 전 -> 여자 실패
                if (datingSurvey.getGender() == Gender.MALE) {
                    datingSurvey.getDatingMatching().getFemaleSurvey().changeMatchStatus(MatchStatus.FAILED);
                }
            }
            else if (datingSurvey.getMatchStatus() == MatchStatus.PAID) {
                if (datingSurvey.getGender() == Gender.FEMALE) {
                    // 여자 탈퇴, 입금 후 -> 남자 환불(NEED_REFUND)
                    if (datingSurvey.getDatingMatching().getPayment().getIsPaid() == true) {
                        datingSurvey.getDatingMatching().getPayment().updateNeedRefundStatus(true);
                        datingSurvey.getDatingMatching().getMaleSurvey().changeMatchStatus(MatchStatus.CANCELED_OR_NEED_REFUND);
                    }
                    // 여자 탈퇴, 입금 전 -> 남자 취소(CANCELED)
                    else {
                        meetingSurvey.getMeetingMatching().getMaleSurvey().changeMatchStatus(MatchStatus.CANCELED_OR_NEED_REFUND);
                    }
                }
                else {
                    // 남자 탈퇴, 입금 후 -> 그대로
                }
            }
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserInfoDto getMyInfo(UserEntity user) {
        return new UserInfoDto(user.getUniversityEmail(), user.getUniversity().getName());
    }

    @Transactional
    public Long testJoin(JoinRequest joinRequest) {
        Optional<UniversityEntity> university = universityRepository.findByDomain("lonessum.com");
        if (university.isPresent()) {
            return userRepository.save(UserEntity.builder()
                    .userName(joinRequest.getUserName())
                    .password(joinRequest.getPassword())
                    .isAdult(true)
                    .isAuthenticated(true)
                    .university(university.get())
                    .build()).getId();
        }
        else {
            throw new RestApiException(UserErrorCode.NO_SUCH_UNIVERSITY);
        }
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
        } else {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }
    }
}
