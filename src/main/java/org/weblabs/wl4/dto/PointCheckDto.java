package org.weblabs.wl4.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PointCheckDto {
    private Long id;
    private Double r;
    private Double x;
    private Double y;
    private Boolean hit;
    private LocalDateTime checkedAt;
    private Long executionTimeMs;
    private Long userId;
}