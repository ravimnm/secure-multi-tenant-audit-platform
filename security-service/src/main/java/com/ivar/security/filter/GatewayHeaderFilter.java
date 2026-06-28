package com.ivar.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.
        SimpleGrantedAuthority;
import org.springframework.security.core.context.
        SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ivar.security.common.context.TenantContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewayHeaderFilter
        extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String userId =
                    request.getHeader("X-User-Id");

            String role =
                    request.getHeader("X-Role");

            String tenantId =
                    request.getHeader("X-Tenant-Id");

            if (userId != null &&
                    role != null &&
                    tenantId != null) {

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                role)));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);

                TenantContext.setTenant(tenantId);
            }

            filterChain.doFilter(request, response);

        } finally {

            TenantContext.clear();
            SecurityContextHolder.clearContext();
        }
    }
}