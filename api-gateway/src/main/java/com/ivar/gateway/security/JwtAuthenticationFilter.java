package com.ivar.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter
        implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtProvider;

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtProvider) {

        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        if (path.startsWith("/auth")
                || exchange.getRequest()
                        .getMethod()
                        .name()
                        .equals("OPTIONS")) {

            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtProvider.validateToken(token)) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        Claims claims =
                jwtProvider.getClaims(token);

        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(r -> r.headers(headers -> {

                            headers.add(
                                    "X-User-Id",
                                    claims.getSubject());

                            headers.add(
                                    "X-Role",
                                    claims.get("role",
                                            String.class));

                            headers.add(
                                    "X-Tenant-Id",
                                    claims.get("tenantId",
                                            String.class));
                        }))
                        .build();

        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}