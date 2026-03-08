package com.pokemon.pokemon_backend.service;

import com.pokemon.pokemon_backend.model.Pokemon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PokemonService {

    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    public List<Pokemon> findAll() {
        return em.createQuery("SELECT p FROM Pokemon p", Pokemon.class)
                .getResultList();
    }

    public Optional<Pokemon> findById(Long id) {
        return Optional.ofNullable(em.find(Pokemon.class, id));
    }

    public List<Pokemon> findByOwnerId(Long ownerId) {
        return em.createQuery("SELECT p FROM Pokemon p WHERE p.owner.id = :oid", Pokemon.class)
                .setParameter("oid", ownerId)
                .getResultList();
    }

    @Transactional
    public Pokemon create(Pokemon pokemon) {
        // Owner must already be set by the resource (authenticated user)
        em.persist(pokemon);
        return pokemon;
    }

    @Transactional
    public Pokemon update(Long pokemonId, Long requesterUserId, Pokemon updated) {
        Pokemon existing = em.find(Pokemon.class, pokemonId);
        if (existing == null) {
            return null; // Resource will return 404
        }

        // Only the owner can update
        if (existing.getOwner() == null || existing.getOwner().getId() == null
                || !existing.getOwner().getId().equals(requesterUserId)) {
            throw new SecurityException("Not owner"); // Resource will return 403
        }

        // Update allowed fields only (never change id/owner)
        existing.setName(updated.getName());
        existing.setHp(updated.getHp());
        existing.setCp(updated.getCp());
        existing.setPicture(updated.getPicture());
        existing.setTypes(updated.getTypes() == null ? List.of() : updated.getTypes());

        return existing;
    }

    @Transactional
    public boolean delete(Long pokemonId, Long requesterUserId) {
        Pokemon existing = em.find(Pokemon.class, pokemonId);
        if (existing == null) {
            return false; // Resource will return 404
        }

        // Only the owner can delete
        if (existing.getOwner() == null || existing.getOwner().getId() == null
                || !existing.getOwner().getId().equals(requesterUserId)) {
            throw new SecurityException("Not owner"); // Resource will return 403
        }

        em.remove(existing);
        return true;
    }

    @Transactional
    public void deleteByOwnerId(Long ownerId) {
        // Delete all pokemons owned by the given user (bulk delete)
        em.createQuery("DELETE FROM Pokemon p WHERE p.owner.id = :oid")
                .setParameter("oid", ownerId)
                .executeUpdate();
    }
}
