package com.yapp.lonessum.domain.abroadArea;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AbroadAreaController {
    private final AbroadAreaService abroadAreaService;

    @GetMapping
    public ResponseEntity<List<AbroadAreaDto>> getAllAreas() {
        return ResponseEntity.ok(abroadAreaService.getAllAbroadAreas());
    }

    //TODO : 이후에 제거
    //지역 정보 등록용 임시 api
    @PostMapping()
    public void registerAbroadAreaInfo() throws IOException {
        abroadAreaService.registerAreaInfo();
    }
}
