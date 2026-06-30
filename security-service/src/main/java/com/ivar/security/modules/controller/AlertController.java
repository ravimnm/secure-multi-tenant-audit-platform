package com.ivar.security.modules.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivar.security.modules.entity.SecurityAlert;
import com.ivar.security.modules.repository.AlertRepository;

@RestController
@RequestMapping("/security/alerts")
public class AlertController {

    private final AlertRepository repository;

    public AlertController(AlertRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SecurityAlert> getAllAlerts() {

        return repository.findAll();
    }

    @GetMapping("/open")
    public List<SecurityAlert> getOpenAlerts() {

        return repository.findByStatus("OPEN");
    }

    @PatchMapping("/{id}/resolve")
    public SecurityAlert resolveAlert(
            @PathVariable Long id) {

        SecurityAlert alert =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Alert not found"));

        alert.setStatus("RESOLVED");

        return repository.save(alert);
    }
}