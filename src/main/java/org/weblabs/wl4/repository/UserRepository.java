package org.weblabs.wl4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.weblabs.wl4.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}