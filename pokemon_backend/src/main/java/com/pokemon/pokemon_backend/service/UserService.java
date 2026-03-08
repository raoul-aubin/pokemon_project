package com.pokemon.pokemon_backend.service;

import com.pokemon.pokemon_backend.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    @Inject
    private PokemonService pokemonService;

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findByUsername(String username) {
        List<User> res = em.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList();
        return res.stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        List<User> res = em.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        return res.stream().findFirst();
    }

    @Transactional
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Transactional
    public Optional<User> updateProfile(Long id, String newUsername, String newEmail) {
        User user = em.find(User.class, id);
        if (user == null) return Optional.empty();

        if (newUsername != null && !newUsername.isBlank() && !newUsername.equals(user.getUsername())) {
            if (findByUsername(newUsername).isPresent()) {
                throw new WebApplicationException("username already used", 409);
            }
            user.setUsername(newUsername);
        }

        if (newEmail != null && !newEmail.isBlank() && !newEmail.equals(user.getEmail())) {
            if (findByEmail(newEmail).isPresent()) {
                throw new WebApplicationException("email already used", 409);
            }
            user.setEmail(newEmail);
        }

        return Optional.of(user);
    }

    @Transactional
    public void changePassword(Long id, String currentPassword, String newPassword) {
        User user = em.find(User.class, id);
        if (user == null) throw new WebApplicationException(404);

        if (currentPassword == null || newPassword == null || newPassword.isBlank()) {
            throw new WebApplicationException(400);
        }

        if (!BCrypt.checkpw(currentPassword, user.getPasswordHash())) {
            throw new WebApplicationException("wrong password", 401);
        }

        user.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
    }

    @Transactional
    public boolean delete(Long id) {
        User u = em.find(User.class, id);
        if (u == null) return false;
        // Ensure dependent pokemons are removed before deleting the user
        pokemonService.deleteByOwnerId(id);
        em.remove(u);
        return true;
    }

}

