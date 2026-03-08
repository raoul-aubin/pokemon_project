package com.pokemon.pokemon_backend.dto;

public class AuthResponse {
    public String token;
    public String tokenType = "Bearer";

    public AuthResponse() {
        // Required by some JSON frameworks / tooling
    }

    public AuthResponse(String token) {
        this.token = token;
    }
}


