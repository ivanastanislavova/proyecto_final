package com.uv.ayuntamientoApp.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
/* 
 * La anotación @Component indica que la clase es un componente
 *  gestionado por Spring, lo que implica:
 * 1. Spring creará automáticamente una instancia de la clase al
 *  arrancar la aplicación (inyección de dependencias).
 * 2. Puede ser inyectada en otras clases usando @Autowired o constructor
 *  injection, facilitando su uso dentro del contexto de Spring.
*/
public class RemoteJwtAuthFilter extends OncePerRequestFilter{
    private static final Logger logger = LoggerFactory.getLogger(RemoteJwtAuthFilter.class);

    private final WebClient webClient;


    /* 
     * El constructor de RemoteJwtAuthFilter toma la URL del 
     * servicio de autenticación desde la configuración
     * (auth.service.url) y construye un WebClient con
     * esa URL base para hacer peticiones HTTP. Esto permite
     * que el filtro luego use ese WebClient
     * para validar tokens remotamente.
    */
    public RemoteJwtAuthFilter(@Value("${apiclient.autenticacion-url}") String authUrl){
        this.webClient = WebClient.builder().baseUrl(authUrl).build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        //String originHeader = request.getHeader("X-Client-Origin");
        String path = request.getRequestURI();
        String method = request.getMethod();

        logger.info("[RemoteJwtAuthFilter] Path: {} Method: {} Authorization: {} X-Client-Origin: {}", path, method, authHeader//, originHeader
        );

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.info("[RemoteJwtAuthFilter] Token recibido: {}", token);
            try {
                Map<String, String> validated = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/auth/validate")
                                .queryParam("token", token)
                                .build())
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();
                logger.info("[RemoteJwtAuthFilter] Validated: {}", validated);
                // Solo permitir POST /api/aparcamiento si cumple todas las condiciones
                if ("/api/aparcamiento".equals(path) && "POST".equalsIgnoreCase(method)) {
                    if (validated != null &&
                        "ROLE_ADMIN".equals(validated.get("role")) &&
                        "admin".equals(validated.get("subject"))) {
                        var authority = new SimpleGrantedAuthority(validated.get("role"));
                        var auth = new UsernamePasswordAuthenticationToken(
                                validated.get("subject"), null, Collections.singletonList(authority));
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        logger.info("[RemoteJwtAuthFilter] Autenticación ADMIN para POST /api/aparcamiento");
                        logger.info("[RemoteJwtAuthFilter] Contexto de seguridad tras setAuthentication: {}", SecurityContextHolder.getContext().getAuthentication());
                        logger.info("[RemoteJwtAuthFilter] Autoridades asignadas: {}", auth.getAuthorities());
                    } else {
                        logger.warn("[RemoteJwtAuthFilter] Rechazado POST /api/aparcamiento por validación estricta: role={}, subject={}, originHeader={}",
                                validated != null ? validated.get("role") : null,
                                validated != null ? validated.get("subject") : null//,
                                //originHeader
                                );
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                } else if (validated != null && validated.containsKey("subject") && validated.containsKey("role")) {
                    // Para otros endpoints, lógica normal
                    var authority = new SimpleGrantedAuthority(validated.get("role"));
                    var auth = new UsernamePasswordAuthenticationToken(
                            validated.get("subject"), null, Collections.singletonList(authority));
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.info("[RemoteJwtAuthFilter] Contexto autenticado: {}", SecurityContextHolder.getContext().getAuthentication());
                    logger.info("[RemoteJwtAuthFilter] Autenticado: {} con rol {}", validated.get("subject"), validated.get("role"));
                    logger.info("[RemoteJwtAuthFilter] Autoridades asignadas: {}", auth.getAuthorities());
                }
            } catch (Exception e) {
                logger.error("[RemoteJwtAuthFilter] Error en validación", e);
            }
        } else {
            logger.warn("[RemoteJwtAuthFilter] Falta o es inválido el header Authorization");
        }

        filterChain.doFilter(request, response);
    }
}
