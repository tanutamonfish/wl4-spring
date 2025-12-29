package org.weblabs.wl4.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointCheck {
    private Long id;
    private Double r;
    private Double x;
    private Double y;
    private Boolean hit;
    private Timestamp checkedAt;
    private Long executionTimeMs;
    private User user;
}