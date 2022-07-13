package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.dto.MatchResultDto;
import com.yapp.lonessum.domain.meeting.dto.PartnerSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UniversityService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingMatchingService {

    private final UniversityService universityService;
    private final MeetingSurveyRepository meetingSurveyRepository;
    private final MeetingMatchingRepository meetingMatchingRepository;

    public MatchResultDto getMatchResult(UserEntity user) {

        MeetingSurveyEntity partnerSurvey;

        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByUser(user).orElseThrow(() -> {
            // 작성한 설문이 없을 때
            throw new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY);
        });

        // 작성한 설문이 있을 때
        // 매칭 참여 안했을 때 -> 가장 최근 매칭 결과
        if (meetingSurvey.getMatchStatus() == MatchStatus.DONE) {
            return new MatchResultDto(7001, SurveyErrorCode.NO_WAITING_SURVEY.getMessage(), null);
        }
        // 매칭 대기중일 떄
        else if (meetingSurvey.getMatchStatus() == MatchStatus.WAITING) {
            return new MatchResultDto(7002, SurveyErrorCode.WAITING_FOR_MATCH.getMessage(), null);
        }
        // 매칭 성공했을 때
        else if (meetingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
            // 내가 남자일 때
            if (meetingSurvey.getGender() == Gender.MALE) {
                partnerSurvey = meetingSurvey.getMeetingMatching().getFemaleSurvey();
                // 내가 결제 안했을 때
                if (meetingSurvey.getPayment() == null) {
                    return new MatchResultDto(7003, SurveyErrorCode.PAY_FOR_MATCH.getMessage(), null);
                }
            }
            // 내가 여자일 때
            else {
                partnerSurvey = meetingSurvey.getMeetingMatching().getMaleSurvey();
                // 상대가 결제 안했을 때
                if (meetingSurvey.getMeetingMatching().getMaleSurvey().getPayment() == null) {
                    return new MatchResultDto(7004, SurveyErrorCode.WAITING_FOR_PAY.getMessage(), null);
                }
            }
            // 모두 결제했을 때 -> 매칭 상대 정보
            PartnerSurveyDto partnerSurveyDto = partnerSurvey.toPartnerSurveyDto();

            List<String> universityNames = universityService.getUniversityNameFromId(partnerSurvey.getOurUniversities());
            partnerSurveyDto.setUniversities(universityNames);

//            partnerSurveyDto.setAreas();
            return new MatchResultDto(7005, SurveyErrorCode.SHOW_MATCH_RESULT.getMessage(), partnerSurveyDto);
        }
        // 매칭 실패했을 떄
        else {
            return new MatchResultDto(7006, SurveyErrorCode.MATCH_FAIL.getMessage(), null);
        }
    }
}
