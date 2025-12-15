package org.weblabs.wl4.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weblabs.wl4.dto.AuthRequest;
import org.weblabs.wl4.dto.AuthResponse;
import org.weblabs.wl4.dto.UserResponse;
import org.weblabs.wl4.entity.User;
import org.weblabs.wl4.security.JwtTokenUtil;
import org.weblabs.wl4.service.AuthService;
import org.weblabs.wl4.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final boolean SECURE = true;

    private ResponseCookie createAuthCookie(String token) {
        return ResponseCookie.from("authToken", token)
                .httpOnly(true)
                .secure(SECURE)
                .sameSite("Strict")
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        User user = userService.register(request.getEmail(), request.getPassword());
        String token = jwtTokenUtil.generateToken(user);
        
        ResponseCookie authCookie = createAuthCookie(token);
        
        AuthResponse response = new AuthResponse(user.getEmail());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        authService.authenticate(request.getEmail(), request.getPassword());
        User user = userService.getByEmail(request.getEmail());
        String token = jwtTokenUtil.generateToken(user);
        
        ResponseCookie authCookie = createAuthCookie(token);
        
        AuthResponse response = new AuthResponse(user.getEmail());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie clearCookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .secure(SECURE)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(new UserResponse(userDetails.getUsername()));
    }
}