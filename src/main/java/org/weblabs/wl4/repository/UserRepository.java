package org.weblabs.wl4.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import org.weblabs.wl4.entity.User;

import java.util.Optional;

import static org.weblabs.wl4.entity.Tables.*;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    
    private final DSLContext dsl;
    
    public Optional<User> findByEmail(String email) {
        Record record = dsl.select()
                .from(USERS)
                .where(USERS_EMAIL.eq(email))
                .fetchOne();
                
        return Optional.ofNullable(record).map(this::mapToUser);
    }
    
    public Optional<User> findById(Long id) {
        Record record = dsl.select()
                .from(USERS)
                .where(USERS_ID.eq(id))
                .fetchOne();
                
        return Optional.ofNullable(record).map(this::mapToUser);
    }
    
    public User save(User user) {
        if (user.getId() == null) {
            // Insert
            Long id = dsl.insertInto(USERS)
                    .set(USERS_EMAIL, user.getEmail())
                    .set(USERS_PASSWORD_HASH, user.getPasswordHash())
                    .returningResult(USERS_ID)
                    .fetchOne()
                    .getValue(USERS_ID);
                    
            user.setId(id);
            return user;
        } else {
            // Update
            dsl.update(USERS)
                    .set(USERS_EMAIL, user.getEmail())
                    .set(USERS_PASSWORD_HASH, user.getPasswordHash())
                    .where(USERS_ID.eq(user.getId()))
                    .execute();
            return user;
        }
    }
    
    public void delete(Long id) {
        dsl.deleteFrom(USERS)
                .where(USERS_ID.eq(id))
                .execute();
    }
    
    private User mapToUser(Record record) {
        User user = new User();
        user.setId(record.get(USERS_ID));
        user.setEmail(record.get(USERS_EMAIL));
        user.setPasswordHash(record.get(USERS_PASSWORD_HASH));
        return user;
    }
}