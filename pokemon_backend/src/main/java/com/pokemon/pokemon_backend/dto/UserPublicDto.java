package com.pokemon.pokemon_backend.dto;

import com.pokemon.pokemon_backend.model.User;

public class UserPublicDto {
    public Long id;
    public String username;
    public String email;

    // Convert User entity to a safe DTO (no passwordHash exposed)
    public static UserPublicDto of(User u) {
        UserPublicDto dto = new UserPublicDto();
        dto.id = u.getId();
        dto.username = u.getUsername();
        dto.email = u.getEmail();
        return dto;
    }
}


