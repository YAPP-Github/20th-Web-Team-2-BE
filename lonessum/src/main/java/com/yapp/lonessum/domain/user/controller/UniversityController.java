package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.dto.UniversityDto;
import com.yapp.lonessum.domain.user.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<List<UniversityDto>> getAllUniversities() {
        return ResponseEntity.ok(universityService.getAllUniversities());
    }
}
