package org.weblabs.wl4.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class PointCheckDto {
    private Long id;
    private Double r;
    private Double x;
    private Double y;
    private Boolean hit;
    private Timestamp checkedAt;
    private Long executionTimeMs;
    private Long userId;
}