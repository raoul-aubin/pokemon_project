package com.pokemon.pokemon_backend.resource;

import com.pokemon.pokemon_backend.dto.UserPublicDto;
import com.pokemon.pokemon_backend.model.Pokemon;
import com.pokemon.pokemon_backend.model.User;
import com.pokemon.pokemon_backend.service.PokemonService;
import com.pokemon.pokemon_backend.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeResource {

    @Inject
    private UserService userService;

    @Inject
    private PokemonService pokemonService;

    @Context
    private SecurityContext securityContext;

    // Resolve the currently authenticated user from SecurityContext (JWT)
    private User currentUserOr401() {
        if (securityContext == null || securityContext.getUserPrincipal() == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String username = securityContext.getUserPrincipal().getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));
    }

    // Return current user's public profile
    @GET
    public UserPublicDto me() {
        return UserPublicDto.of(currentUserOr401());
    }

    public static class UpdateMeRequest {
        public String username;
        public String email;
    }

    // Update username/email for current user
    @PUT
    public UserPublicDto updateMe(UpdateMeRequest req) {
        if (req == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        User u = currentUserOr401();

        User updated = userService.updateProfile(u.getId(), req.username, req.email)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));

        return UserPublicDto.of(updated);
    }

    public static class ChangePasswordRequest {
        public String currentPassword;
        public String newPassword;
    }

    // Change password for current user (requires current password)
    @PUT
    @Path("/password")
    public Response changePassword(ChangePasswordRequest req) {
        if (req == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        User u = currentUserOr401();
        userService.changePassword(u.getId(), req.currentPassword, req.newPassword);
        return Response.noContent().build();
    }

    // Delete current account
    @DELETE
    public Response deleteMe() {
        User u = currentUserOr401();
        boolean deleted = userService.delete(u.getId()); // <-- fixed method name
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    // Return all Pokémons created by the current user
    @GET
    @Path("/pokemons")
    public List<Pokemon> myPokemons() {
        User u = currentUserOr401();
        return pokemonService.findByOwnerId(u.getId());
    }
}
