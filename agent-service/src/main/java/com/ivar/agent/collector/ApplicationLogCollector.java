package com.ivar.agent.collector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLogCollector {

    @Value("${agent.log.path}")
    private String logPath;

    public List<String> collectLogs() {

        try {

            return Files.readAllLines(
                    Path.of(logPath));

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to read log file", e);
        }
    }
}