package dev.alexgiou.authservice.repository;

import dev.alexgiou.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Alexandros Giounan
 * @code @created: 5/28/2025
 */

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
