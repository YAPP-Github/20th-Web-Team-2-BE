package com.yapp.lonessum.mapper;

import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MeetingSurveyMapper extends GenericMapper<MeetingSurveyDto, MeetingSurveyEntity>{
}
