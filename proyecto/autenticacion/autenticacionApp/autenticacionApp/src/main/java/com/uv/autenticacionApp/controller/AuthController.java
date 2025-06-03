package com.uv.autenticacionApp.controller;

import com.uv.autenticacionApp.model.IssuedToken;
import com.uv.autenticacionApp.repository.IssuedTokenRepository;
import com.uv.autenticacionApp.service.TokenService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IssuedTokenRepository repository;
    private final TokenService tokenService;

    public AuthController(IssuedTokenRepository repository, TokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(
            @RequestParam String subject,
            @RequestParam String role) {

        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        IssuedToken issuedToken = new IssuedToken();
        issuedToken.setToken(token);
        issuedToken.setSubject(subject);
        issuedToken.setRole(role);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< "+role);
        issuedToken.setIssuedAt(now);
        issuedToken.setExpiresAt(now.plusDays(365));

        repository.save(issuedToken);
                System.out.println(token);
        return ResponseEntity.ok(token);
    }
    
    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestParam String token) {
        System.out.println("Validando token recibido: " + token);

        return tokenService.validateToken(token)
                .map(t -> {
                    System.out.println("Token válido - Subject: " + t.getSubject() + ", Role: " + t.getRole());
                    return ResponseEntity.ok(Map.of(
                            "subject", t.getSubject(),
                            "role", t.getRole()));
                })
                .orElseGet(() -> {
                    System.out.println("Token inválido o expirado");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                });
    }

}
