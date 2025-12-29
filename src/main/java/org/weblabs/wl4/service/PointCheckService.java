package org.weblabs.wl4.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weblabs.wl4.dto.PointCheckDto;
import org.weblabs.wl4.dto.PointCheckRequest;
import org.weblabs.wl4.entity.PointCheck;
import org.weblabs.wl4.entity.User;
import org.weblabs.wl4.repository.PointCheckRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointCheckService {
    
    private final PointCheckRepository pointCheckRepository;
    
    public boolean isPointInArea(double r, double x, double y) {
        if (x == 0) {
            return -r / 2 <= y && y <= r;
        } else if (y == 0) {
            return -r <= x && x <= r;
        } else if (x < 0 && y < 0) {
            return false;
        } else if (x < 0 && y > 0) {
            return y <= x + r;
        } else if (x > 0 && y > 0) {
            return x <= r && y <= r;
        } else if (x > 0 && y < 0) {
            return x * x + y * y <= r * r / 4;
        }
        return false;
    }
    
    @Transactional
    public PointCheckDto checkAndSave(PointCheckRequest request, User user) {
        long startTime = System.currentTimeMillis();
        boolean hit = isPointInArea(request.getR(), request.getX(), request.getY());
        long executionTime = System.currentTimeMillis() - startTime;
        
        PointCheck pointCheck = new PointCheck();
        pointCheck.setR(request.getR());
        pointCheck.setX(request.getX());
        pointCheck.setY(request.getY());
        pointCheck.setHit(hit);
        pointCheck.setExecutionTimeMs(executionTime);
        pointCheck.setCheckedAt(Timestamp.from(Instant.now()));
        pointCheck.setUser(user);
        
        PointCheck saved = pointCheckRepository.save(pointCheck);
        
        return mapToDto(saved);
    }
    
    public Page<PointCheckDto> getUserPoints(Long userId, Pageable pageable) {
        return pointCheckRepository.findByUserId(userId, pageable).map(this::mapToDto);
    }

    public Page<PointCheckDto> getUserPointsByRadius(Long userId, Double radius, PageRequest pageRequest) {
        Page<PointCheck> points = pointCheckRepository.findByUserIdAndR(userId, radius, pageRequest);
        return points.map(this::mapToDto);
    }
    
    private PointCheckDto mapToDto(PointCheck pointCheck) {
        PointCheckDto dto = new PointCheckDto();
        dto.setId(pointCheck.getId());
        dto.setR(pointCheck.getR());
        dto.setX(pointCheck.getX());
        dto.setY(pointCheck.getY());
        dto.setHit(pointCheck.getHit());
        dto.setCheckedAt(pointCheck.getCheckedAt());
        dto.setExecutionTimeMs(pointCheck.getExecutionTimeMs());
        dto.setUserId(pointCheck.getUser().getId());
        return dto;
    }
}