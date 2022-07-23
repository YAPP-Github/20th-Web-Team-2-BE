package com.yapp.lonessum.domain.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class HealthCheckController {
    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("health check!");
    }
}
