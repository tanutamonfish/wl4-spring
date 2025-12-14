package org.weblabs.wl4.service;

import org.springframework.stereotype.Service;
import org.weblabs.wl4.dto.PointCheckRequest;
import org.weblabs.wl4.dto.PointCheckResponse;

import lombok.val;

@Service
public class AreaCheckService {

    protected boolean isPointInArea(double r, double x, double y) {
        if (x == 0) {
            return -r / 2 <= y && y <= r;
        } else if (y == 0) {
            return -r <= x && x <= r;
        } else if (x < 0 && y < 0) {
            return false;
        } else if (x < 0 && y > 0) {
            return y <= x + r;
        } else if (x > 0 && y > 0) {
            return x <= r && y <= r;
        } else if (x > 0 && y < 0) {
            return x * x + y * y <= r * r / 4;
        }

        return false;
    }

    public PointCheckResponse checkPoint(PointCheckRequest checkRequest) {
        val r = checkRequest.getR();
        val x = checkRequest.getX();
        val y = checkRequest.getY();

        val result = isPointInArea(r, x, y);
        return PointCheckResponse.of(r, x, y, result);
    }
}
