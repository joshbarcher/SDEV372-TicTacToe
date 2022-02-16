package com.example.tictactoerest.repositories;

import com.example.tictactoerest.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A data layer for persisting Player objects.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Repository
public interface IPlayerRepository extends JpaRepository<Player, Integer>
{
    /**
     * Returns a player by name.
     *
     * @param name the player name
     * @return a Player object wrapped in an Optional
     */
    Optional<Player> findByNameEquals(String name);
}
