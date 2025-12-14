package org.weblabs.wl4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weblabs.wl4.dto.PointCheckRequest;
import org.weblabs.wl4.dto.PointCheckResponse;
import org.weblabs.wl4.service.AreaCheckService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/area")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class AreaCheckController {
    
    private final AreaCheckService areaCheckService;
    
    @PostMapping("/check")
    public ResponseEntity<PointCheckResponse> checkPoint(
            @Valid @RequestBody PointCheckRequest request) {
        return ResponseEntity.ok(areaCheckService.checkPoint(request));
    }
}