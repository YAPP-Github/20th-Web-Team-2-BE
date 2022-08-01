package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
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
    private final MeetingMatchingRepository meetingMatchingRepository;
    private final DatingMatchingRepository datingMatchingRepository;

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
            //남자 탈퇴 (여자는 환불 필요 x)
            if (meetingSurvey.getGender() == Gender.MALE) {
                //TODO : 만약 남자가 탈퇴했을 때에 여자쪽에 추가 조치가 필요한 해당 부분에 반영
                meetingSurvey.getMeetingMatching().getFemaleSurvey().changeMatchStatus(MatchStatus.FAILED);
            } else if (meetingSurvey.getGender() == Gender.FEMALE) {
                //여자 탈퇴 (남자 환불 필요 o)
                Optional<MeetingMatchingEntity> meetingMatchingEntity =
                        meetingMatchingRepository.findWithFeMaleSurvey(meetingSurvey.getId());

                if (meetingMatchingEntity.isPresent()) {
                    PaymentEntity payment = meetingMatchingEntity.get().getPayment();

                    payment.updateNeedRefundStatus(true);

                    //TODO : 남자 유저 환불 대상임 알림 코드 필요하면 추가 위치
                    meetingSurvey.getMeetingMatching().getMaleSurvey().changeMatchStatus(MatchStatus.NEED_REFUND);
                }
            }
        }

        //dating case
        if (!Objects.isNull(datingSurvey)) {
            //남자 탈퇴 (여자는 환불 필요 x)
            if (datingSurvey.getGender() == Gender.MALE) {
                //TODO : 만약 남자가 탈퇴했을 때에 여자쪽에 추가 조치가 필요한 해당 부분에 반영
                datingSurvey.getDatingMatching().getFemaleSurvey().changeMatchStatus(MatchStatus.FAILED);
            } else if (datingSurvey.getGender() == Gender.FEMALE) {
                //여자 탈퇴 (남자 환불 필요 o)
                Optional<DatingMatchingEntity> datingMatchingEntity =
                        datingMatchingRepository.findWithFeMaleSurvey(datingSurvey.getId());

                if (datingMatchingEntity.isPresent()) {
                    PaymentEntity payment = datingMatchingEntity.get().getPayment();

                    payment.updateNeedRefundStatus(true);

                    //TODO : 남자 유저 환불 대상임 알림 코드 필요하면 추가 위치
                    datingSurvey.getDatingMatching().getMaleSurvey().changeMatchStatus(MatchStatus.NEED_REFUND);
                }
            }
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserInfoDto getMyInfo() {
        UserEntity user = jwtService.getUserFromJwt();
        return new UserInfoDto(user.getUniversityEmail(), user.getUniversity().getName());
    }

    @Transactional
    public Long testJoin(JoinRequest joinRequest) {
        UniversityEntity university = universityRepository.findByDomain("google.com");
        universityRepository.save(university);
        return userRepository.save(UserEntity.builder()
                .userName(joinRequest.getUserName())
                .password(joinRequest.getPassword())
                .isAdult(true)
                .isAuthenticated(true)
                .university(university)
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
        } else {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }
    }
}
