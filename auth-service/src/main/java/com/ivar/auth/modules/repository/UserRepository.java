package com.ivar.auth.modules.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivar.auth.modules.entity.User;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}