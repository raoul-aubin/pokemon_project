package com.pokemon.pokemon_backend.resource;

import com.pokemon.pokemon_backend.dto.PokemonDto;
import com.pokemon.pokemon_backend.dto.PokemonRequest;
import com.pokemon.pokemon_backend.model.Pokemon;
import com.pokemon.pokemon_backend.model.User;
import com.pokemon.pokemon_backend.service.PokemonService;
import com.pokemon.pokemon_backend.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;

@Path("/pokemons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonResource {

    @Inject
    private PokemonService pokemonService;

    @Inject
    private UserService userService;

    private PokemonDto toDto(Pokemon p) {
        return new PokemonDto(
                p.getId(),
                p.getName(),
                p.getHp(),
                p.getCp(),
                p.getPicture(),
                p.getCreated(),
                p.getTypes()
        );
    }

    private Pokemon toEntity(PokemonRequest req) {
        Pokemon p = new Pokemon();
        p.setName(req.name);
        p.setHp(req.hp);
        p.setCp(req.cp);
        p.setPicture(req.picture);
        p.setTypes(req.types == null ? new ArrayList<>() : new ArrayList<>(req.types));
        return p;
    }

    @GET
    public List<PokemonDto> getAllPokemons() {
        return pokemonService.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response getPokemonById(@PathParam("id") Long id) {
        return pokemonService.findById(id)
                .map(p -> Response.ok(toDto(p)))
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/search")
    public List<PokemonDto> searchPokemons(@QueryParam("name") String name) {

        if (name == null || name.isBlank()) {
            return List.of();
        }

        return pokemonService.searchByName(name)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @POST
    public Response createPokemon(PokemonRequest req, @Context SecurityContext sc) {

        if (sc.getUserPrincipal() != null) {
            if (req != null && req.name != null && !req.name.isBlank()) {
                String username = sc.getUserPrincipal().getName();
                User owner = userService.findByUsername(username)
                        .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

                Pokemon pokemon = toEntity(req);
                pokemon.setId(null);
                pokemon.setOwner(owner);

                pokemonService.create(pokemon);

                return Response.status(Response.Status.CREATED)
                        .entity(toDto(pokemon))
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    @PUT
    @Path("/{id}")
    public Response updatePokemon(@PathParam("id") Long id, PokemonRequest req, @Context SecurityContext sc) {

        if (sc.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (req == null || req.name == null || req.name.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String username = sc.getUserPrincipal().getName();
        User requester = userService.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

        try {
            Pokemon updatedEntity = toEntity(req);
            Pokemon result = pokemonService.update(id, requester.getId(), updatedEntity);

            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(toDto(result)).build();

        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePokemon(@PathParam("id") Long id, @Context SecurityContext sc) {

        if (sc.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String username = sc.getUserPrincipal().getName();
        User requester = userService.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

        try {
            boolean deleted = pokemonService.delete(id, requester.getId());

            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.noContent().build();

        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}