package com.yapp.lonessum.domain.admin;

import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.email.service.EmailService;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.payment.entity.MatchType;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final EmailService emailService;
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

    @Transactional
    public PaymentStatusDto setUserMeetingPayment(PaymentDto paymentDto) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        meetingSurvey.getMeetingMatching().getPayment().payForMatching(MatchType.MEETING);

        String emailA = meetingSurvey.getMeetingMatching().getMaleSurvey().getUser().getUniversityEmail();
        String emailB = meetingSurvey.getMeetingMatching().getFemaleSurvey().getUser().getUniversityEmail();

        try {
            emailService.sendPartnerSurvey(emailA);
            emailService.sendPartnerSurvey(emailB);
        } catch (MessagingException e) {
            log.warn("매칭 결과 이메일 전송 실패", emailA);
            log.warn("매칭 결과 이메일 전송 실패", emailB);
            throw new RestApiException(UserErrorCode.FAIL_TO_SEND_EMAIL);
        }
        return new PaymentStatusDto(meetingSurvey.getMeetingMatching().getPayment().getIsPaid());
    }

    @Transactional
    public PaymentStatusDto setUserDatingPayment(PaymentDto paymentDto) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        datingSurvey.getDatingMatching().getPayment().payForMatching(MatchType.DATING);

        String emailA = datingSurvey.getDatingMatching().getMaleSurvey().getUser().getUniversityEmail();
        String emailB = datingSurvey.getDatingMatching().getFemaleSurvey().getUser().getUniversityEmail();

        try {
            emailService.sendPartnerSurvey(emailA);
            emailService.sendPartnerSurvey(emailB);
        } catch (MessagingException e) {
            log.warn("매칭 결과 이메일 전송 실패", emailA);
            log.warn("매칭 결과 이메일 전송 실패", emailB);
            throw new RestApiException(UserErrorCode.FAIL_TO_SEND_EMAIL);
        }
        return new PaymentStatusDto(datingSurvey.getDatingMatching().getPayment().getIsPaid());
    }

    @Transactional(readOnly = true)
    public List<RefundTargetDto> getMeetingRefundTargets() {
        List<MeetingMatchingEntity> paymentTargetList = meetingMatchingRepository.findPaymentTargetList();

        return paymentTargetList.stream()
                .map(p -> new RefundTargetDto(
                                p.getMaleSurvey().getKakaoId(),
                                p.getFemaleSurvey().getKakaoId(),
                                p.getPayment().getPayName(),
                                p.getPayment().getIsNeedRefund()
                        )
                )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RefundTargetDto> getDatingRefundTargets() {
        List<DatingMatchingEntity> paymentTargetList = datingMatchingRepository.findPaymentTargetList();

        return paymentTargetList.stream()
                .map(p -> new RefundTargetDto(
                                p.getMaleSurvey().getKakaoId(),
                                p.getFemaleSurvey().getKakaoId(),
                                p.getPayment().getPayName(),
                                p.getPayment().getIsNeedRefund()
                        )
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentStatusDto setUserMeetingRefund(PaymentDto paymentDto) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        meetingSurvey.getMeetingMatching().getPayment().updateNeedRefundStatus(false);

        return new PaymentStatusDto(meetingSurvey.getMeetingMatching().getPayment().getIsNeedRefund());
    }

    @Transactional
    public PaymentStatusDto setUserDatingRefund(PaymentDto paymentDto) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByKakaoId(paymentDto.getKakaoId())
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY));
        datingSurvey.getDatingMatching().getPayment().updateNeedRefundStatus(false);

        return new PaymentStatusDto(datingSurvey.getDatingMatching().getPayment().getIsNeedRefund());
    }
}
