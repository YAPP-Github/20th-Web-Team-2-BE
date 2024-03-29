package com.yapp.lonessum.domain.university;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDto {
    private Long id;
    private String name;
}
