package com.cloudstorage.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Returns a structured JSON 401 response when an unauthenticated request
 * hits a protected endpoint — instead of Spring's default HTML error page.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest     request,
                         HttpServletResponse    response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success",   false);
        body.put("message",   "Authentication required. Please login to access this resource.");
        body.put("errorCode", "UNAUTHORIZED");
        body.put("timestamp", Instant.now().toString());
        body.put("path",      request.getRequestURI());

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
