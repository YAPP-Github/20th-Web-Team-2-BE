package com.yapp.lonessum.domain.dating.dto;

import com.yapp.lonessum.domain.constant.Body;
import com.yapp.lonessum.domain.constant.Characteristic;
import com.yapp.lonessum.domain.constant.DateCount;
import com.yapp.lonessum.domain.constant.Department;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DatingPartnerSurveyDto {
    private int age;
    private int height;
    private Body body;
    private String university;
    private Department department;
    private List<String> areas;
    private DateCount dateCount;
    private Characteristic characteristic;
    private Boolean isSmoke;
    private String kakaoId;
}
