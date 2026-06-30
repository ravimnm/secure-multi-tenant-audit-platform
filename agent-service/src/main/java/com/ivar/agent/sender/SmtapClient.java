package com.ivar.agent.sender;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ivar.agent.entity.AuditEvent;

@Component
public class SmtapClient {

    private final RestTemplate restTemplate =
            new RestTemplate();

    @Value("${smtap.audit.url}")
    private String auditUrl;
    @Value("${agent.api-key}")
    private String apiKey;

    public void send(AuditEvent event) {

        HttpHeaders headers =
                new HttpHeaders();

        headers.set(
                "X-API-KEY",
                apiKey);

        HttpEntity<AuditEvent> request =
                new HttpEntity<>(
                        event,
                        headers);

        restTemplate.postForEntity(
                auditUrl,
                request,
                String.class);

        System.out.println(
                "Sent event: "
                + event.getAction());
    }
}