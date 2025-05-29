package dev.alexgiou.authservice.service;

import dev.alexgiou.authservice.model.User;

import dev.alexgiou.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Alexandros Giounan
 * @code @created: 5/28/2025
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
