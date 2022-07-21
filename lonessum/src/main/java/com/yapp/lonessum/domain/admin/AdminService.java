package com.yapp.lonessum.domain.admin;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MeetingSurveyRepository meetingSurveyRepository;
    private final DatingSurveyRepository datingSurveyRepository;

    @Transactional(readOnly = true)
    public List<UserStatusDto> getUserMeetingStatusList() {
        List<MeetingSurveyEntity> meetingSurveyList = meetingSurveyRepository.findAll();
        List<UserStatusDto> userStatusDtoList = new ArrayList<>();
        for (MeetingSurveyEntity ms : meetingSurveyList) {
            boolean isPaid;
            if (ms.getMatchStatus().equals(MatchStatus.PAID)) {
                isPaid = true;
            }
            else {
                isPaid = false;
            }
            userStatusDtoList.add(UserStatusDto.builder()
                    .kakaoId(ms.getKakaoId())
                    .matchStatus(ms.getMatchStatus())
                    .isPaid(isPaid)
                    .build());
        }
        return userStatusDtoList;
    }

    @Transactional(readOnly = true)
    public List<UserStatusDto> getUserDatingStatusList() {
        List<DatingSurveyEntity> datingSurveyList = datingSurveyRepository.findAll();
        List<UserStatusDto> userStatusDtoList = new ArrayList<>();
        for (DatingSurveyEntity ms : datingSurveyList) {
            boolean isPaid;
            if (ms.getMatchStatus().equals(MatchStatus.PAID)) {
                isPaid = true;
            }
            else {
                isPaid = false;
            }
            userStatusDtoList.add(UserStatusDto.builder()
                    .kakaoId(ms.getKakaoId())
                    .matchStatus(ms.getMatchStatus())
                    .isPaid(isPaid)
                    .build());
        }
        return userStatusDtoList;
    }

    @Transactional
    public void setUserMeetingPayment(PaymentDto paymentDto) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        if (meetingSurvey.getMatchStatus().equals(MatchStatus.MATCHED)) {
            meetingSurvey.changeMatchStatus(MatchStatus.PAID);
        }
    }

    @Transactional
    public void setUserDatingPayment(PaymentDto paymentDto) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        if (datingSurvey.getMatchStatus().equals(MatchStatus.MATCHED)) {
            datingSurvey.changeMatchStatus(MatchStatus.PAID);
        }
    }
}
