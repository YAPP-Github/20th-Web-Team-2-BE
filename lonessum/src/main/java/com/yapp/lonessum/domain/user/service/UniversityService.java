package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.entity.UniversityEntity;
import com.yapp.lonessum.domain.user.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UniversityService {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    UniversityRepository universityRepository;

    public void registerUniInfo() throws IOException {
        List<UniversityEntity> csvList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:university/university_info.csv");

        File csv = new File(resource.getURI());
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                String[] lineArr = line.split(","); // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.

                UniversityEntity university = new UniversityEntity();
                university.setName(lineArr[0]);
                university.setDomain(lineArr[1]);

                universityRepository.save(university);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
