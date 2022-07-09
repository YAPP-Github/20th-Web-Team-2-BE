package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingMatchingService {

    private final MeetingSurveyRepository meetingSurveyRepository;
    private final MeetingMatchingRepository meetingMatchingRepository;

    public Object getMatchResult(UserEntity user) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByUser(user).orElseThrow(() -> {
            // 작성한 설문이 없을 때
            throw new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY);
        });

        // 작성한 설문이 있을 때
        // 매칭 참여 안했을 때
        if (meetingSurvey.getMatchStatus() == MatchStatus.DONE) {
            throw new RestApiException(SurveyErrorCode.NO_WAITING_SURVEY);
        }
        // 매칭 대기중일 떄
        else if (meetingSurvey.getMatchStatus() == MatchStatus.WAITING) {
            throw new RestApiException(SurveyErrorCode.WAITING_FOR_MATCH);
        }
        // 매칭 성공했을 때
        else if (meetingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
            // 내가 남자일 때
            if (meetingSurvey.getGender() == Gender.MALE) {
                // 내가 결제 안했을 때
                if (meetingSurvey.getPayment() == null) {
                    throw new RestApiException(SurveyErrorCode.PAY_FOR_MATCH);
                }
            }
            // 내가 여자일 때
            else {
                // 상대가 결제 안했을 때
                if (meetingSurvey.getMeetingMatching().getMaleSurvey().getPayment() == null) {
                    throw new RestApiException(SurveyErrorCode.WAITING_FOR_PAY);
                }
            }
            // 모두 결제했을 때

        }
        return null;
    }
}
