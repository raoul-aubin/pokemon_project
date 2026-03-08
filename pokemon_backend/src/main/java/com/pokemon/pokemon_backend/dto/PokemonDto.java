package com.pokemon.pokemon_backend.dto;

import java.time.LocalDate;
import java.util.List;

public class PokemonDto {

    public Long id;
    public String name;
    public int hp;
    public int cp;
    public String picture;
    public LocalDate created;
    public List<String> types;

    public PokemonDto() {}

    public PokemonDto(Long id, String name, int hp, int cp,
                      String picture, LocalDate created, List<String> types) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.cp = cp;
        this.picture = picture;
        this.created = created;
        this.types = types;
    }
}
