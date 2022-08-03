package com.yapp.lonessum.domain.dating.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyDatingSurveyDto {
    private DatingSurveyDto datingSurveyDto;
    private List<String> stringAbroadAreas;
}
