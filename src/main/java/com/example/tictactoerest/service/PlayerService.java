package com.example.tictactoerest.service;

import com.example.tictactoerest.entities.Game;
import com.example.tictactoerest.entities.Player;
import com.example.tictactoerest.exceptions.GameStateException;
import com.example.tictactoerest.exceptions.InvalidNameException;
import com.example.tictactoerest.exceptions.MissingPlayerException;
import com.example.tictactoerest.repositories.IGameRepository;
import com.example.tictactoerest.repositories.IPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Contains business logic for creating and tracking players
 * in tictactoe games.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Service
public class PlayerService
{
    private IPlayerRepository playerRepo;
    private IGameRepository gameRepo;

    /**
     * Creates a new service layer with data layers for
     * games and players.
     *
     * @param playerRepo the player data layer
     * @param gameRepo the game data layer
     */
    public PlayerService(IPlayerRepository playerRepo,
                         IGameRepository gameRepo)
    {
        this.playerRepo = playerRepo;
        this.gameRepo = gameRepo;
    }

    /**
     * Returns a list of games the input player was part of.
     *
     * @param player the player to search for
     * @return a list of games
     */
    public List<Game> getGamesPlayed(Player player)
    {
        return gameRepo.findAllByPlayerXEqualsOrPlayerOEquals(player, player);
    }

    /**
     * Saves a new player to the api.
     *
     * @param player the new player
     * @throws InvalidNameException if the name of the player is empty
     * @throws GameStateException if a duplicate name is found
     * @return the saved player after being persisted
     */
    public Player save(Player player)
    {
        if (player.getName().isEmpty())
        {
            throw new InvalidNameException("Name cannot be empty");
        }
        else if (playerByName(player.getName()).isPresent())
        {
            throw new GameStateException("Duplicate name found!");
        }

        return playerRepo.save(player);
    }

    /**
     * Returns a list of saved players.
     * @return a list of players
     */
    public List<Player> players()
    {
        return playerRepo.findAll();
    }

    /**
     * Finds a player with the given id.
     * @param playerId the player id to search for
     * @throws MissingPlayerException thrown when the player is not found
     * @return the player with the matching id
     */
    public Player playerById(int playerId)
    {
        Optional<Player> player = playerRepo.findById(playerId);
        if (player.isEmpty())
        {
            throw new MissingPlayerException(String.format("Player with id %s missing", playerId));
        }
        return player.get();
    }

    /**
     * Searches for a player by name.
     *
     * @param name the name to search for
     * @return returns the name of the player wrapped in an Optional
     */
    public Optional<Player> playerByName(String name)
    {
        return playerRepo.findByNameEquals(name);
    }

    /**
     * Updates a player object.
     *
     * @param updatedPlayer the updated player object
     * @throws MissingPlayerException if the player is not found
     * @return the saved player object with updated values
     */
    public Player updatePlayer(Player updatedPlayer)
    {
        int playerId = updatedPlayer.getPlayerId();
        Optional<Player> savedPlayer = playerRepo.findById(playerId);
        if (savedPlayer.isEmpty())
        {
            throw new MissingPlayerException(String.format("Player with id %s missing", playerId));
        }
        Player player = savedPlayer.get();
        player.setName(updatedPlayer.getName());
        return playerRepo.save(player);
    }

    /**
     * Deletes a player with the matching id.
     * @param playerId the player id to search for
     */
    public void deletePlayer(int playerId)
    {
        Optional<Player> savedPlayer = playerRepo.findById(playerId);
        if (savedPlayer.isEmpty())
        {
            throw new MissingPlayerException(String.format("Player with id %s missing", playerId));
        }
        playerRepo.deleteById(playerId);
    }

    @Override
    public String toString()
    {
        return "A games service managing " + playerRepo.count() + " players.";
    }
}
