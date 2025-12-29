package org.weblabs.wl4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointCheck {
    private Long id;
    private Double r;
    private Double x;
    private Double y;
    private Boolean hit;
    private LocalDateTime checkedAt;
    private Long executionTimeMs;
    private User user;
}