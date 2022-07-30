package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.dto.AbroadAreaDto;
import com.yapp.lonessum.domain.user.entity.AbroadAreaEntity;
import com.yapp.lonessum.domain.user.entity.UniversityEntity;
import com.yapp.lonessum.domain.user.repository.AbroadAreaRepository;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AbroadAreaService {

    private final ResourceLoader resourceLoader;

    private final AbroadAreaRepository abroadAreaRepository;

    public void registerAreaInfo() throws IOException {
        File csv = new File("/root/app/city_info.csv");
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                AbroadAreaEntity abroadArea = new AbroadAreaEntity();
                abroadArea.setName(line);

                abroadAreaRepository.save(abroadArea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<AbroadAreaDto> getAllAbroadAreas() {
        List<AbroadAreaEntity> abroadAreas = abroadAreaRepository.findAll();
        List<AbroadAreaDto> abroadAreaDtoList = new ArrayList<>();
        abroadAreas.forEach(area -> {
            abroadAreaDtoList.add(AbroadAreaDto.builder()
                    .id(area.getId())
                    .name(area.getName())
                    .build());
        });

        return abroadAreaDtoList;
    }

    public List<String> getAreaNameFromId(List<Long> areaIdList) {
        List<String> areaNames = new ArrayList<>();
        for (Long areaId : areaIdList) {
            AbroadAreaEntity abroadArea = abroadAreaRepository.findById(areaId).orElseThrow(() -> new RestApiException(UserErrorCode.NO_SUCH_AREA));
            String areaName = abroadArea.getName();
            areaNames.add(areaName.substring(areaName.lastIndexOf(",")+1));
        }
        return areaNames;
    }
}
