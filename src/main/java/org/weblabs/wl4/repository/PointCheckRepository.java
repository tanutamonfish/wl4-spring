package org.weblabs.wl4.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.weblabs.wl4.entity.PointCheck;
import org.weblabs.wl4.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.weblabs.wl4.entity.Tables.*;

@Repository
@RequiredArgsConstructor
public class PointCheckRepository {

    private final DSLContext dsl;

    public Page<PointCheck> findByUserId(Long userId, Pageable pageable) {
        int total = dsl.fetchCount(
                dsl.select().from(POINT_CHECKS)
                        .where(POINT_CHECKS_USER_ID.eq(userId)));

        SelectSeekStep1<Record, Timestamp> query = dsl.select()
                .from(POINT_CHECKS)
                .where(POINT_CHECKS_USER_ID.eq(userId))
                .orderBy(POINT_CHECKS_CHECKED_AT.desc());

        List<PointCheck> content = query
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch()
                .map(this::mapToPointCheck);

        return new PageImpl<>(content, pageable, total);
    }

    public Page<PointCheck> findByUserIdAndR(Long userId, Double r, Pageable pageable) {
        int total = dsl.fetchCount(
                dsl.select().from(POINT_CHECKS)
                        .where(POINT_CHECKS_USER_ID.eq(userId)
                                .and(POINT_CHECKS_R.eq(r))));

        SelectSeekStep1<Record, Timestamp> query = dsl.select()
                .from(POINT_CHECKS)
                .where(POINT_CHECKS_USER_ID.eq(userId)
                        .and(POINT_CHECKS_R.eq(r)))
                .orderBy(POINT_CHECKS_CHECKED_AT.desc());

        List<PointCheck> content = query
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch()
                .map(this::mapToPointCheck);

        return new PageImpl<>(content, pageable, total);
    }

    public PointCheck save(PointCheck pointCheck) {
        if (pointCheck.getId() == null) {
            Long id = dsl.insertInto(POINT_CHECKS)
                    .set(POINT_CHECKS_R, pointCheck.getR())
                    .set(POINT_CHECKS_X, pointCheck.getX())
                    .set(POINT_CHECKS_Y, pointCheck.getY())
                    .set(POINT_CHECKS_HIT, pointCheck.getHit())
                    .set(POINT_CHECKS_CHECKED_AT, pointCheck.getCheckedAt())
                    .set(POINT_CHECKS_EXECUTION_TIME_MS, pointCheck.getExecutionTimeMs())
                    .set(POINT_CHECKS_USER_ID, pointCheck.getUser().getId())
                    .returningResult(POINT_CHECKS_ID)
                    .fetchOne()
                    .getValue(POINT_CHECKS_ID);

            pointCheck.setId(id);
            return pointCheck;
        } else {
            dsl.update(POINT_CHECKS)
                    .set(POINT_CHECKS_R, pointCheck.getR())
                    .set(POINT_CHECKS_X, pointCheck.getX())
                    .set(POINT_CHECKS_Y, pointCheck.getY())
                    .set(POINT_CHECKS_HIT, pointCheck.getHit())
                    .set(POINT_CHECKS_CHECKED_AT, pointCheck.getCheckedAt())
                    .set(POINT_CHECKS_EXECUTION_TIME_MS, pointCheck.getExecutionTimeMs())
                    .set(POINT_CHECKS_USER_ID, pointCheck.getUser().getId())
                    .where(POINT_CHECKS_ID.eq(pointCheck.getId()))
                    .execute();

            return pointCheck;
        }
    }

    public Optional<PointCheck> findById(Long id) {
        Record record = dsl.select()
                .from(POINT_CHECKS)
                .where(POINT_CHECKS_ID.eq(id))
                .fetchOne();

        return Optional.ofNullable(record).map(this::mapToPointCheck);
    }

    public void delete(Long id) {
        dsl.deleteFrom(POINT_CHECKS)
                .where(POINT_CHECKS_ID.eq(id))
                .execute();
    }

    public void deleteByUserId(Long userId) {
        dsl.deleteFrom(POINT_CHECKS)
                .where(POINT_CHECKS_USER_ID.eq(userId))
                .execute();
    }

    private PointCheck mapToPointCheck(Record record) {
        PointCheck pointCheck = new PointCheck();
        pointCheck.setId(record.get(POINT_CHECKS_ID));
        pointCheck.setR(record.get(POINT_CHECKS_R));
        pointCheck.setX(record.get(POINT_CHECKS_X));
        pointCheck.setY(record.get(POINT_CHECKS_Y));
        pointCheck.setHit(record.get(POINT_CHECKS_HIT));
        pointCheck.setCheckedAt(record.get(POINT_CHECKS_CHECKED_AT));
        pointCheck.setExecutionTimeMs(record.get(POINT_CHECKS_EXECUTION_TIME_MS));

        User user = new User();
        user.setId(record.get(POINT_CHECKS_USER_ID));
        pointCheck.setUser(user);

        return pointCheck;
    }
}