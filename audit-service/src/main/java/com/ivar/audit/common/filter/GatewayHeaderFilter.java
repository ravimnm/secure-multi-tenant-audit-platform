package com.ivar.audit.common.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ivar.audit.common.context.TenantContext;
import com.ivar.audit.common.context.UserContext;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

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

            String tenant =
                    request.getHeader("X-Tenant-Id");

            String user =
                    request.getHeader("X-User-Id");

            String role =
                    request.getHeader("X-Role");

            System.out.println("Tenant = " + tenant);
            System.out.println("User = " + user);
            System.out.println("Role = " + role);

            TenantContext.setTenant(tenant);
            UserContext.setUser(user);
            UserContext.setRole(role);

            filterChain.doFilter(request, response);

        } finally {

            TenantContext.clear();
            UserContext.clear();
        }
    }
}