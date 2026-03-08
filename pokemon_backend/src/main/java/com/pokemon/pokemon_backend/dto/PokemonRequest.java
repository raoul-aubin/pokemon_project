package com.pokemon.pokemon_backend.dto;

import java.util.List;

public class PokemonRequest {

    public String name;
    public int hp;
    public int cp;
    public String picture;
    public List<String> types;

    public PokemonRequest() {}
}
