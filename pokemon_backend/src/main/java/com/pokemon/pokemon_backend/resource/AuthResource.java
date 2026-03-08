package com.pokemon.pokemon_backend.resource;

import com.pokemon.pokemon_backend.dto.AuthResponse;
import com.pokemon.pokemon_backend.dto.LoginRequest;
import com.pokemon.pokemon_backend.dto.RegisterRequest;
import com.pokemon.pokemon_backend.model.User;
import com.pokemon.pokemon_backend.security.JwtService;
import com.pokemon.pokemon_backend.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private UserService userService;

    @Inject
    private JwtService jwtService;

    // Register a new user and return a JWT token (auto-login)
    @POST
    @Path("/register")
    public Response register(RegisterRequest req) {

        if (req == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String username = (req.username == null) ? null : req.username.trim();
        String email = (req.email == null) ? null : req.email.trim().toLowerCase();
        String password = req.password;

        if (username == null || username.isBlank()
                || email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Check uniqueness
        if (userService.findByUsername(username).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("username already used"))
                    .build();
        }
        if (userService.findByEmail(email).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("email already used"))
                    .build();
        }

        // Hash password before storing (never store plain password)
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        User u = new User(null, username, email, hash);
        userService.create(u);

        // Issue JWT token
        String token = jwtService.createToken(u.getId(), u.getUsername());

        return Response.status(Response.Status.CREATED)
                .entity(new AuthResponse(token))
                .build();
    }

    // Login using username + password, return a JWT token
    @POST
    @Path("/login")
    public Response login(LoginRequest req) {

        if (req == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String username = (req.username == null) ? null : req.username.trim();
        String password = req.password;

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User u = userService.findByUsername(username).orElse(null);

        // Return the same response for "user not found" and "wrong password"
        if (u == null || !BCrypt.checkpw(password, u.getPasswordHash())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = jwtService.createToken(u.getId(), u.getUsername());
        return Response.ok(new AuthResponse(token)).build();
    }

    // Simple JSON error response payload
    public static class ErrorResponse {
        public String message;

        public ErrorResponse() {}
        public ErrorResponse(String message) { this.message = message; }
    }
}


