package com.yapp.lonessum.domain.university;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<List<UniversityDto>> getAllUniversities() {
        return ResponseEntity.ok(universityService.getAllUniversities());
    }

    //TODO : 이후에 제거
    //대학교 정보 등록용 임시 api
    @PostMapping()
    public void registerUniversityInfo() throws IOException {
        universityService.registerUniInfo();
    }
}
