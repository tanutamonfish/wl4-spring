package org.weblabs.wl4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.weblabs.wl4.dto.AuthRequest;
import org.weblabs.wl4.dto.AuthResponse;
import org.weblabs.wl4.dto.UserResponse;
import org.weblabs.wl4.entity.User;
import org.weblabs.wl4.service.AuthService;
import org.weblabs.wl4.service.UserService;
import org.weblabs.wl4.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        User user = userService.register(request.getEmail(), request.getPassword());
        String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        authService.authenticate(request.getEmail(), request.getPassword());
        User user = userService.getByEmail(request.getEmail());
        String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(new UserResponse(userDetails.getUsername()));
    }
}