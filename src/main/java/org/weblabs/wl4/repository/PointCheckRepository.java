package org.weblabs.wl4.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weblabs.wl4.entity.PointCheck;

@Repository
public interface PointCheckRepository extends JpaRepository<PointCheck, Long> {
    Page<PointCheck> findByUserId(Long userId, Pageable pageable);

    Page<PointCheck> findByUserIdAndR(Long userId, Double r, Pageable pageable);
}