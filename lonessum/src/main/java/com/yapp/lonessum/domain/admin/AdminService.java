package com.yapp.lonessum.domain.admin;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.payment.entity.MatchType;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MeetingSurveyRepository meetingSurveyRepository;
    private final DatingSurveyRepository datingSurveyRepository;
    private final MeetingMatchingRepository meetingMatchingRepository;
    private final DatingMatchingRepository datingMatchingRepository;

    @Transactional(readOnly = true)
    public List<PaymentTargetDto> getMeetingPaymentTargetList() {
        List<MeetingMatchingEntity> paymentTargetList = meetingMatchingRepository.findPaymentTargetList();

        return paymentTargetList.stream()
                .map(p -> new PaymentTargetDto(
                                p.getMaleSurvey().getKakaoId(),
                                p.getFemaleSurvey().getKakaoId(),
                                p.getPayment().getPayName(),
                                p.getPayment().getIsPaid()
                        )
                )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PaymentTargetDto> getDatingPaymentTargetList() {
        List<DatingMatchingEntity> paymentTargetList = datingMatchingRepository.findPaymentTargetList();

        return paymentTargetList.stream()
                .map(p -> new PaymentTargetDto(
                                p.getMaleSurvey().getKakaoId(),
                                p.getFemaleSurvey().getKakaoId(),
                                p.getPayment().getPayName(),
                                p.getPayment().getIsPaid()
                        )
                )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserStatusDto> getUserMeetingStatusList() {
        List<MeetingSurveyEntity> meetingSurveyList = meetingSurveyRepository.findAll();
        List<UserStatusDto> userStatusDtoList = new ArrayList<>();
        for (MeetingSurveyEntity ms : meetingSurveyList) {
            boolean isPaid;
            if (ms.getMatchStatus().equals(MatchStatus.PAID)) {
                isPaid = true;
            } else {
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
            } else {
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
    public PaymentStatusDto setUserMeetingPayment(PaymentDto paymentDto) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        meetingSurvey.getMeetingMatching().getPayment().payForMatching(MatchType.MEETING);
        return new PaymentStatusDto(meetingSurvey.getMeetingMatching().getPayment().getIsPaid());
    }

    @Transactional
    public PaymentStatusDto setUserDatingPayment(PaymentDto paymentDto) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        datingSurvey.getDatingMatching().getPayment().payForMatching(MatchType.DATING);
        return new PaymentStatusDto(datingSurvey.getDatingMatching().getPayment().getIsPaid());
    }
}
