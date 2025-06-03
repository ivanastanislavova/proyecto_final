package com.uv.estacionApp.security;

import java.util.Map;

public class AuthorizedTokens {
    public static final Map<String, String> TOKENS = Map.ofEntries(
        // "token" => "role:identifier"
        Map.entry("admin-token-123", "admin:global"),
        Map.entry("estacion-token-1", "estaciones:1"),
        Map.entry("estacion-token-2", "estaciones:2"),
        Map.entry("estacion-token-3", "estaciones:3"),
        Map.entry("estacion-token-4", "estaciones:4"),
        Map.entry("estacion-token-5", "estaciones:5"),
        Map.entry("estacion-token-6", "estaciones:6"),
        Map.entry("estacion-token-7", "estaciones:7"),
        Map.entry("estacion-token-8", "estaciones:8"),
        Map.entry("estacion-token-9", "estaciones:9"),
        Map.entry("estacion-token-10", "estaciones:10")
    );

    public static boolean isValid(String token) {
        return TOKENS.containsKey(token);
    }

    public static String getRole(String token) {
        return TOKENS.getOrDefault(token, "").split(":")[0];
    }

    public static String getSubject(String token) {
        return TOKENS.getOrDefault(token, "").split(":")[1];
    }
}

