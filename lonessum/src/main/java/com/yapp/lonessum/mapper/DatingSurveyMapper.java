package com.yapp.lonessum.mapper;

import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DatingSurveyMapper extends GenericMapper<DatingSurveyDto, DatingSurveyEntity>{
}
