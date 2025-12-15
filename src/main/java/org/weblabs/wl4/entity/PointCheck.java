package org.weblabs.wl4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_checks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointCheck {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double r;
    private Double x;
    private Double y;
    private Boolean hit;
    private LocalDateTime checkedAt;
    private Long executionTimeMs;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @PrePersist
    protected void onCreate() {
        checkedAt = LocalDateTime.now();
    }
}