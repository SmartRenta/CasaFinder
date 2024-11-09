package com.smart_renta.casa_finder.repository;

import com.smart_renta.casa_finder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Number id);
    Optional<User> findByEmail(String email);
}