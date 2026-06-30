package com.ivar.auth.modules.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivar.auth.modules.dto.ApiKeyResponse;
import com.ivar.auth.modules.dto.ApiKeyValidationResponse;
import com.ivar.auth.modules.dto.LoginRequest;
import com.ivar.auth.modules.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @GetMapping("/validate-api-key")
    public ResponseEntity<ApiKeyResponse> validateApiKey(
            @RequestHeader("X-API-KEY")
            String apiKey) {

        return authService.validateApiKey(apiKey);
    }
}