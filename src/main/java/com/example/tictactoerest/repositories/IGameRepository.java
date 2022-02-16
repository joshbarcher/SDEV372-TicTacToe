package com.example.tictactoerest.repositories;

import com.example.tictactoerest.entities.Game;
import com.example.tictactoerest.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A data layer for persisting Game objects.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Repository
public interface IGameRepository extends JpaRepository<Game, Integer>
{
    /**
     * A custom query method that returns all games with one of the
     * two input players. These are usually passed the same player
     * to find matches where the player is involved.
     *
     * @param playerX a player
     * @param playerO a player
     * @return a list of games with one of the input players
     */
    List<Game> findAllByPlayerXEqualsOrPlayerOEquals(Player playerX, Player playerO);
}
