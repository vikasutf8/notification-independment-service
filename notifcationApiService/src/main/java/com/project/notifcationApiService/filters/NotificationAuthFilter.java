package com.project.notifcationApiService.filters;

import com.project.notifcationApiService.constant.ApplicationConstants;
import com.project.notifcationApiService.exception.UnauthorizedException;
import com.project.notifcationApiService.models.contexts.NotificationContext;
import com.project.notifcationApiService.models.contexts.NotificationContextHolder;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Authentication filter for API requests.
 * Validates that X-Tenant-Id header is present for all /api requests.
 * Sets the tenant ID in NotificationContextHolder for propagation across the request.
 */
@Component
public class NotificationAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest httpRequest) {
                String requestPath = httpRequest.getRequestURI();

                // Check if request path starts with /api
                if (requestPath.startsWith(ApplicationConstants.API_PREFIX)) {
                    String tenantIdHeader = httpRequest.getHeader(ApplicationConstants.TENANT_ID_HEADER);

                    // Validate tenant ID is present and not empty
                    if (UtilsMehtods.isEmpty(tenantIdHeader)) {
                        throw new UnauthorizedException(ApplicationConstants.TENANT_ID_MISSING);
                    }

                    // Convert String to UUID and set tenant ID in NotificationContextHolder
                    UUID tenantId = UUID.fromString(tenantIdHeader);
                    NotificationContextHolder.setContext(new NotificationContext(tenantId));
                }
            }
            chain.doFilter(request, response);
        } finally {
            NotificationContextHolder.clearContext();
        }
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
