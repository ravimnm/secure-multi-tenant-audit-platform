package com.ivar.auth.modules.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ivar.auth.modules.dto.CreateUserRequest;
import com.ivar.auth.modules.dto.UserResponse;
import com.ivar.auth.modules.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse createUser(

            @Valid
            @RequestBody
            CreateUserRequest request) {

        return service.createUser(request);
    }

    @PostMapping("/bulk")
    public List<UserResponse> createUsers(

            @RequestBody
            List<CreateUserRequest> requests) {

        return service.createUsers(requests);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {

        return service.getAllUsers();
    }
}