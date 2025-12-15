package org.weblabs.wl4.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weblabs.wl4.dto.PointCheckDto;
import org.weblabs.wl4.dto.PointCheckRequest;
import org.weblabs.wl4.entity.User;
import org.weblabs.wl4.service.PointCheckService;
import org.weblabs.wl4.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointCheckController {

    private final PointCheckService pointCheckService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PointCheckDto> checkPoint(
            @Valid @RequestBody PointCheckRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getByEmail(userDetails.getUsername());

        PointCheckDto result = pointCheckService.checkAndSave(request, user);

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<PointCheckDto>> getUserPoints(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "checkedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        User user = userService.getByEmail(userDetails.getUsername());

        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<PointCheckDto> points = pointCheckService.getUserPoints(user.getId(), pageRequest);

        return ResponseEntity.ok(points);
    }
}