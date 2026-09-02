package com.cloudstorage.service;

import com.cloudstorage.dto.auth.AuthResponse;
import com.cloudstorage.dto.auth.LoginRequest;
import com.cloudstorage.dto.auth.RegisterRequest;
import com.cloudstorage.dto.auth.UserResponse;
import com.cloudstorage.exception.BadRequestException;
import com.cloudstorage.exception.ResourceNotFoundException;
import com.cloudstorage.model.User;
import com.cloudstorage.model.enums.UserRole;
import com.cloudstorage.repository.UserRepository;
import com.cloudstorage.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for authentication.
 *
 * Architecture:
 *   AuthController → AuthService → UserRepository
 *
 * JWT is handled by JwtService.
 * Password hashing is handled by the PasswordEncoder bean (BCrypt).
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService      jwtService;

    // ── Register ──────────────────────────────────────────────────────────────

    @Transactional
    public void register(RegisterRequest request) {
        String email = request.getEmail().toLowerCase().trim();

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("An account with this email already exists.");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setProvider("LOCAL");

        userRepository.save(user);
        log.info("New user registered: {}", email);
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().toLowerCase().trim();

        // Use a generic error message to prevent user enumeration
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid email or password."));

        if (user.getPasswordHash() == null ||
                !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Invalid email or password.");
        }

        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name()
        );

        log.info("User logged in: {}", email);

        return AuthResponse.builder()
                .token(token)
                .user(toUserResponse(user))
                .build();
    }

    // ── Current User ──────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return toUserResponse(user);
    }

    // ── Mapper ────────────────────────────────────────────────────────────────

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .provider(user.getProvider())
                .build();
    }
}
