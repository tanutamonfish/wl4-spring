package org.weblabs.wl4.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String email;

    public UserResponse(String email) {
        this.email = email;
    }
}