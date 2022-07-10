package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingMatchingService {

    private final MeetingSurveyRepository meetingSurveyRepository;
    private final MeetingMatchingRepository meetingMatchingRepository;

    public Object getMatchResult(UserEntity user) {
        Optional<MeetingSurveyEntity> meetingSurvey = meetingSurveyRepository.findByUser(user);
        // 작성한 설문이 없을 때
        if (meetingSurvey.isEmpty()) {
            throw new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY);
        }
        // 작성한 설문이 있을 때
        else {
            // 매칭 대기중일 떄
            if (meetingSurvey.get().getMatchStatus() == MatchStatus.WAITING) {
                throw new RestApiException(SurveyErrorCode.WAITING_FOR_MATCH);
            }
            // 매칭 성공했을 때
            else if (meetingSurvey.get().getMatchStatus() == MatchStatus.MATCHED) {
                // 내가 결제 안했을 때
                
                // 상대가 결제 안했을 때
                
                // 모두 결제 했을 때

// 여자는 결제 안해도 된다는 요구사항
            }
        }
        return null;
    }
}
