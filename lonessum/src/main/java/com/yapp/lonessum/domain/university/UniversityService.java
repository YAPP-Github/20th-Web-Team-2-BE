package com.yapp.lonessum.domain.university;

import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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
public class UniversityService {

    private final ResourceLoader resourceLoader;

    private final UniversityRepository universityRepository;

    public void registerUniInfo() throws IOException {
        File csv = new File("/root/app/university_info.csv");
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

    public boolean isSupportedUniversity(String email) {
        int idx = email.indexOf("@");
        return universityRepository.existsByDomain(email.substring(idx+1));
    }

    public List<UniversityDto> getAllUniversities() {
        List<UniversityEntity> universityList = universityRepository.findAll();
        List<UniversityDto> universityDtoList = new ArrayList<>();
        universityList.forEach(university -> {
            universityDtoList.add(UniversityDto.builder()
                    .id(university.getId())
                    .name(university.getName())
                    .build());
        });
        return universityDtoList;
    }

    public List<String> getUniversityNameFromId(List<Long> universityIdList) {
        List<String> universityNames = new ArrayList<>();
        for (Long universityId : universityIdList) {
            UniversityEntity universityEntity = universityRepository.findById(universityId).orElseThrow(() -> new RestApiException(UserErrorCode.NO_SUCH_UNIVERSITY));
            universityNames.add(universityEntity.getName());
        }
        return universityNames;
    }

//     @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
//        registerUniInfo();
        UniversityEntity university1 = new UniversityEntity();
        university1.setDomain("google.com");
        university1.setName("GOOGLE");
        UniversityEntity university2 = new UniversityEntity();
        university2.setDomain("naver.com");
        university2.setName("NAVER");
        UniversityEntity university3 = new UniversityEntity();
        university3.setDomain("kakao.com");
        university3.setName("KAKAO");
        universityRepository.save(university1);
        universityRepository.save(university2);
        universityRepository.save(university3);
    }
}
