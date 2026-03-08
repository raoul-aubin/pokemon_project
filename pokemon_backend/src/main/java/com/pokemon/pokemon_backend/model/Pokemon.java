package com.pokemon.pokemon_backend.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int hp;
    private int cp;

    @Column(length = 500)
    private String picture;

    @Column(nullable = false, updatable = false)
    private LocalDate created;

    @ElementCollection
    @CollectionTable(
            name = "pokemon_type",
            joinColumns = @JoinColumn(name = "pokemon_id")
    )
    @Column(name = "type", nullable = false)
    private List<String> types = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonbTransient
    private User owner;

    public Pokemon() {}

    // Optionnel: constructeur pratique
    public Pokemon(Long id, String name, int hp, int cp, String picture, LocalDate created, List<String> types, User owner) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.cp = cp;
        this.picture = picture;
        this.created = created;
        this.types = types != null ? types : new ArrayList<>();
        this.owner = owner;
    }

    @PrePersist
    void onCreate() {
        if (created == null) created = LocalDate.now();
        if (picture == null || picture.isBlank()) {
            picture = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/xxx.png";
        }
        if (types == null) types = new ArrayList<>();
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getCp() { return cp; }
    public void setCp(int cp) { this.cp = cp; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public LocalDate getCreated() { return created; }
    public void setCreated(LocalDate created) { this.created = created; }

    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}