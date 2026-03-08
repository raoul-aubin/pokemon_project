package com.pokemon.pokemon_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import jakarta.enterprise.context.ApplicationScoped;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@ApplicationScoped
public class JwtService {

    // IMPORTANT: mets une vraie valeur via variable d’environnement ou config Payara
    // Exemple: 64+ chars. Ne commit pas une vraie clé en public.
    private static final String SECRET =
            System.getProperty("JWT_SECRET",
                    "CHANGE_ME_CHANGE_ME_CHANGE_ME_CHANGE_ME_CHANGE_ME_CHANGE_ME_123456");

    private static final long EXP_MS = 1000L * 60 * 60 * 24; // 24h

    private Key key() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long userId, String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + EXP_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("uid", userId)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }
}

