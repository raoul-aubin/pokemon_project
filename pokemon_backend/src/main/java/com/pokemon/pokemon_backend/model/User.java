package com.pokemon.pokemon_backend.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    @JsonbTransient // Never expose password hash in JSON responses
    private String passwordHash;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Pokemon> pokemons = new ArrayList<>();

    public User() {}

    public User(Long id, String username, String email, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public List<Pokemon> getPokemons() { return pokemons; }

    // Avoid null list assignment that would break add/remove helpers
    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = (pokemons == null) ? new ArrayList<>() : pokemons;
    }

    // Keep both sides of the relationship in sync
    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
        pokemon.setOwner(this);
    }

    // Keep both sides of the relationship in sync
    public void removePokemon(Pokemon pokemon) {
        pokemons.remove(pokemon);
        pokemon.setOwner(null);
    }
}

