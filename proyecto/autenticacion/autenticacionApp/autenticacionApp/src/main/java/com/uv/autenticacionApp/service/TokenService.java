package com.uv.autenticacionApp.service;

import com.uv.autenticacionApp.model.IssuedToken;
import com.uv.autenticacionApp.repository.IssuedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private final IssuedTokenRepository tokenRepository;

    @Autowired
    public TokenService(IssuedTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(String subject, String role) {
        LocalDateTime now = LocalDateTime.now();

        // Buscar si ya existe un token válido para ese subject y role
        return tokenRepository.findAll().stream()
                .filter(t -> t.getSubject().equals(subject) &&
                            t.getRole().equals(role) &&
                            t.getExpiresAt().isAfter(now))
                .findFirst()
                .map(IssuedToken::getToken)
                .orElseGet(() -> {
                    // Si no hay token válido, crear uno nuevo
                    String token = UUID.randomUUID().toString();
                    LocalDateTime expiry = now.plusYears(1);

                    IssuedToken issuedToken = new IssuedToken();
                    issuedToken.setToken(token);
                    issuedToken.setSubject(subject);
                    issuedToken.setRole(role);
                    issuedToken.setIssuedAt(now);
                    issuedToken.setExpiresAt(expiry);

                    tokenRepository.save(issuedToken);
                    return token;
                });
    }


    public Optional<IssuedToken> validateToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()));
    }
}
