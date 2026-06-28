package com.ivar.auth.modules.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivar.auth.modules.dto.CreateUserRequest;
import com.ivar.auth.modules.dto.UserResponse;
import com.ivar.auth.modules.entity.Tenant;
import com.ivar.auth.modules.entity.User;
import com.ivar.auth.modules.repository.TenantRepository;
import com.ivar.auth.modules.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            TenantRepository tenantRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(
            CreateUserRequest request) {

        if (userRepository.existsByUsername(
                request.username())) {

            throw new RuntimeException(
                    "Username already exists");
        }

        Tenant tenant =
                tenantRepository
                        .findByTenantId(
                                request.tenantId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Tenant not found"));

        User user = new User();

        user.setUsername(request.username());

        user.setPassword(
                passwordEncoder.encode(
                        request.password()));

        user.setEmail(request.email());

        user.setRole(request.role());

        user.setTenant(tenant);

        User saved =
                userRepository.save(user);

        return map(saved);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public List<UserResponse> createUsers(
            List<CreateUserRequest> requests) {

        return requests.stream()
                .map(this::createUser)
                .toList();
    }

    private UserResponse map(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getTenant().getTenantId()
        );
    }
}