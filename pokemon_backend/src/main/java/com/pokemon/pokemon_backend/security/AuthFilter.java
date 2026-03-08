package com.pokemon.pokemon_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    private JwtService jwtService;

    @Override
    public void filter(ContainerRequestContext ctx) {

        if ("OPTIONS".equalsIgnoreCase(ctx.getMethod())) {
            return;
        }

        String path = ctx.getUriInfo().getPath(); // e.g. "auth/login", "pokemons"
        String method = ctx.getMethod();

        // Public endpoints: /auth/* and GET /pokemons*
        if (path.equals("auth") || path.startsWith("auth/")) {
            return;
        }
        if (path.startsWith("pokemons") && method.equalsIgnoreCase("GET")) {
            return;
        }

        // Extract and validate Bearer token from Authorization header
        String header = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = header.substring("Bearer ".length()).trim();

        try {
            Jws<Claims> jws = jwtService.parse(token);
            Claims c = jws.getBody();

            String username = c.getSubject();
            Long uid = ((Number) c.get("uid")).longValue();

            // Store auth data for downstream usage (optional)
            ctx.setProperty("auth.username", username);
            ctx.setProperty("auth.uid", uid);

            // Provide a SecurityContext so resources can access the authenticated username
            ctx.setSecurityContext(new jakarta.ws.rs.core.SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return false;
                }

                @Override
                public boolean isSecure() {
                    return "https".equalsIgnoreCase(ctx.getUriInfo().getRequestUri().getScheme());
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });

        } catch (Exception e) {
            // Invalid token (expired, bad signature, malformed, etc.)
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
