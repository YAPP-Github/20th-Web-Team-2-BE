package com.yapp.lonessum.mapper;

import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeetingSurveyMapper extends GenericMapper<MeetingSurveyDto, MeetingSurveyEntity>{
}
