package com.uv.autenticacionApp.repository;

import com.uv.autenticacionApp.model.IssuedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssuedTokenRepository extends JpaRepository<IssuedToken, String> {
    Optional<IssuedToken> findByToken(String token);
}
