package com.ivar.agent.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ivar.agent.collector.ApplicationLogCollector;
import com.ivar.agent.entity.AuditEvent;
import com.ivar.agent.sender.SmtapClient;

@Component
public class CollectionScheduler {

    private final ApplicationLogCollector collector;

    private final SmtapClient client;

    @Value("${agent.actor-id}")
    private String actorId;

    public CollectionScheduler(
            ApplicationLogCollector collector,
            SmtapClient client) {

        this.collector = collector;
        this.client = client;
    }

    @Scheduled(fixedDelay = 30000)
    public void collectAndSend() {

        System.out.println("SCHEDULER RUNNING...");

        List<String> logs =
                collector.collectLogs();

        System.out.println("Found logs: " + logs.size());

        for (String line : logs) {

            System.out.println(
                    "Processing: " + line);

            AuditEvent event =
                    new AuditEvent(
                            line,
                            actorId,
                            null
                    );

            client.send(event);
        }
    }
}