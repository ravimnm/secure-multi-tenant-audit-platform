package com.ivar.auth.modules.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivar.auth.modules.dto.CreateUserRequest;
import com.ivar.auth.modules.dto.LoginRequest;
import com.ivar.auth.modules.dto.UserResponse;
import com.ivar.auth.modules.entity.Tenant;
import com.ivar.auth.modules.entity.User;
import com.ivar.auth.modules.repository.TenantRepository;
import com.ivar.auth.modules.repository.UserRepository;
import com.ivar.auth.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtProvider;

    public AuthService(UserRepository userRepository,
                       TenantRepository tenantRepository,
                       PasswordEncoder encoder,JwtTokenProvider jwtProvider) {

        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.encoder = encoder;
        this.jwtProvider=jwtProvider;
    }

    public UserResponse createUser(
            CreateUserRequest request) {

        Tenant tenant =
                tenantRepository.findByTenantId(
                        request.tenantId())
                        .orElseThrow(() ->
                                new RuntimeException("Tenant not found"));

        User user = new User();

        user.setUsername(request.username());
        user.setPassword(
                encoder.encode(request.password()));

        user.setEmail(request.email());
        user.setRole(request.role());
        user.setTenant(tenant);

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole(),
                tenant.getTenantId()
        );
    }

    public List<UserResponse> getUsers() {

        return userRepository.findAll()
                .stream()
                .map(user ->
                        new UserResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getRole(),
                                user.getTenant()
                                        .getTenantId()
                        ))
                .toList();
    }
    public String login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username"));

        if (!encoder.matches(
                request.password(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid password");
        }

        return jwtProvider.generateToken(
                user.getUsername(),
                user.getRole(),
                user.getTenant().getTenantId());
    }
}