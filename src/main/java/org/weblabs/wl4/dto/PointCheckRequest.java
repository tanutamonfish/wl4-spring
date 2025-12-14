package org.weblabs.wl4.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PointCheckRequest {
    
    @NotNull(message = "Radius is required")
    @Min(value = 0, message = "Radius must be between 0 and 3")
    @Max(value = 3, message = "Radius must be between 0 and 3")
    private Double r;
    
    @NotNull(message = "X coordinate is required")
    @Min(value = -5, message = "X must be between -5 and 3")
    @Max(value = 3, message = "X must be between -5 and 3")
    private Double x;
    
    @NotNull(message = "Y coordinate is required")
    @Min(value = -3, message = "Y must be between -3 and 3")
    @Max(value = 3, message = "Y must be between -3 and 3")
    private Double y;
}