package com.redbus.securitydetails.repository;


import com.redbus.securitydetails.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user,Long> {
    Optional<user> findByEmail(String email);
     Optional<user> findByUsernameOrEmail(String username, String email);
    Optional<user> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
