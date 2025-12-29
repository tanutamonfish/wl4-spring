package org.weblabs.wl4.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.weblabs.wl4.entity.User;

import java.util.Optional;

import static org.weblabs.wl4.entity.Tables.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final DSLContext dsl;

    public Optional<User> findByEmail(String email) {
        return dsl.select()
                .from(USERS)
                .where(USERS_EMAIL.eq(email))
                .fetchOptionalInto(User.class);
    }

    public Optional<User> findById(Long id) {
        return dsl.select()
                .from(USERS)
                .where(USERS_ID.eq(id))
                .fetchOptionalInto(User.class);
    }

    public User insert(User user) {
        return dsl.insertInto(USERS)
                .set(USERS_EMAIL, user.getEmail())
                .set(USERS_PASSWORD_HASH, user.getPasswordHash())
                .returning(USERS_ID, USERS_EMAIL, USERS_PASSWORD_HASH)
                .fetchOneInto(User.class);
    }

    public User update(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update");
        }

        dsl.update(USERS)
                .set(USERS_EMAIL, user.getEmail())
                .set(USERS_PASSWORD_HASH, user.getPasswordHash())
                .where(USERS_ID.eq(user.getId()))
                .execute();
        return user;
    }

    public void delete(Long id) {
        dsl.deleteFrom(USERS)
                .where(USERS_ID.eq(id))
                .execute();
    }
}