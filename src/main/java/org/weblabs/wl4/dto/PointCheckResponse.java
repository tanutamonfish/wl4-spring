package org.weblabs.wl4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointCheckResponse {
    private Double r;
    private Double x;
    private Double y;
    private Boolean hit;
    private LocalDateTime checkedAt;
    
    public static PointCheckResponse of(Double r, Double x, Double y, Boolean hit) {
        return new PointCheckResponse(
            r, x, y, hit, 
            LocalDateTime.now()
        );
    }
}