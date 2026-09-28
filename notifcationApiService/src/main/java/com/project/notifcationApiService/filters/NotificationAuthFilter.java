package com.project.notifcationApiService.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.notifcationApiService.constant.ApplicationConstants;
import com.project.notifcationApiService.constant.ErrorCodes;
import com.project.notifcationApiService.models.contexts.NotificationContext;
import com.project.notifcationApiService.models.contexts.NotificationContextHolder;
import com.project.notifcationApiService.utils.commonHelper.ErrorResponse;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.project.notifcationApiService.constant.ApplicationConstants.REQUEST_ID_HEADER;

/**
 * Authentication filter for API requests.
 * Runs once per request; validates X-Tenant-Id for all /api requests,
 * propagates tenant and request IDs via context and MDC.
 * Missing or malformed tenant identity is rejected with 403 at filter level.
 */
@Component
@RequiredArgsConstructor
public class NotificationAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            if (request.getRequestURI().startsWith(ApplicationConstants.API_PREFIX)) {
                String tenantIdHeader = request.getHeader(ApplicationConstants.TENANT_ID_HEADER);

                if (UtilsMehtods.isEmpty(tenantIdHeader)) {
                    reject(response, ApplicationConstants.TENANT_ID_MISSING);
                    return;
                }

                if (!isUuidFormat(tenantIdHeader.trim())) {
                    reject(response, ApplicationConstants.TENANT_ID_INVALID);
                    return;
                }

                String requestId = request.getHeader(REQUEST_ID_HEADER);
                if (UtilsMehtods.isEmpty(requestId)) {
                    requestId = UtilsMehtods.randomGenerateUUID();
                }

                MDC.put(REQUEST_ID_HEADER, requestId);
                response.setHeader(REQUEST_ID_HEADER, requestId);
                NotificationContextHolder.setContext(
                        new NotificationContext(tenantIdHeader.trim(), requestId, false));
            }
            chain.doFilter(request, response);
        } finally {
            NotificationContextHolder.clearContext();
            MDC.remove(REQUEST_ID_HEADER);
        }
    }

    private boolean isUuidFormat(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private void reject(HttpServletResponse response, String message) throws IOException {
        ErrorResponse body = ErrorResponse.of(
                HttpServletResponse.SC_FORBIDDEN, ErrorCodes.FORBIDDEN + ": " + message);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
