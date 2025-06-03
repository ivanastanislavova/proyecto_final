package com.uv.projectApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class RemoteJwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RemoteJwtAuthFilter.class);
    private final WebClient webClient;

    public RemoteJwtAuthFilter(@Value("${auth.service.url}") String authUrl) {
        this.webClient = WebClient.builder().baseUrl(authUrl).build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String originHeader = request.getHeader("X-Client-Origin");
        String path = request.getRequestURI();
        String method = request.getMethod();

        logger.info("[RemoteJwtAuthFilter] Path: {} Method: {} Authorization: {} X-Client-Origin: {}",
                path, method, (authHeader != null ? authHeader.substring(0, Math.min(10, authHeader.length())) + "..." : "null"), originHeader);
        logger.info("<<<<<<<<<<<<<<<<<<PATH={}>>>>>>>>>>>>>>>>>>METHOD={}", path,method);
        if ("/api/aparcamiento".equals(path) && "POST".equalsIgnoreCase(method)) {
            logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  {}", isValidAdminRequest(authHeader, originHeader, request));
            if (isValidAdminRequest(authHeader, originHeader, request)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                logger.warn("[RemoteJwtAuthFilter] Access denied to POST /api/aparcamiento");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Forbidden\"}");
                throw new ServletException("403 Forbidden generado por RemoteJwtAuthFilter en projectApp");
            }
        }

        // Procesamiento estándar
        processStandardAuth(authHeader, request);

        filterChain.doFilter(request, response);
    }

    private boolean isValidAdminRequest(String authHeader, String originHeader, HttpServletRequest request) {
        logger.warn("<<<<<<<<<<<<<AUTH_HEADER={}>>>>>>>", authHeader);
        logger.warn("<<<<<<<<<<<<<ORIGIN_HEADER={}>>>>>>>", originHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.info("[RemoteJwtAuthFilter] Token recibido para admin: {}", token);
            try {
                Map<String, String> validated = validateToken(token);
                logger.info("VALIDATED SUBJECT VALUE: {}", validated.get("subject"));
                if (validated != null &&
                    "ROLE_ADMIN".equals(validated.get("role")) &&
                    "admin".equals(validated.get("subject")) &&
                    "acceso-ayuntamiento".equals(originHeader)) {

                    logger.info("[RemoteJwtAuthFilter] Admin access granted.");
                    setAuthentication(validated, request);
                    return true;
                }
            } catch (Exception e) {
                logger.error("[RemoteJwtAuthFilter] Error during admin validation", e);
            }
        }
        return false;
    }

    private void processStandardAuth(String authHeader, HttpServletRequest request) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Map<String, String> validated = validateToken(token);
                if (validated != null && validated.containsKey("subject") && validated.containsKey("role")) {
                    setAuthentication(validated, request);
                }
            } catch (Exception e) {
                logger.error("[RemoteJwtAuthFilter] Error during token validation", e);
            }
        }
    }

    private Map<String, String> validateToken(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auth/validate")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .block();
    }

    private void setAuthentication(Map<String, String> validated, HttpServletRequest request) {
        var authority = new SimpleGrantedAuthority(validated.get("role"));
        var auth = new UsernamePasswordAuthenticationToken(
                validated.get("subject"), null, Collections.singletonList(authority));
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
