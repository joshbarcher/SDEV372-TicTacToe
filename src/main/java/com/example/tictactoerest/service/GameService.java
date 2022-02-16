package com.example.tictactoerest.service;

import com.example.tictactoerest.commands.GameMove;
import com.example.tictactoerest.entities.Game;
import com.example.tictactoerest.entities.Player;
import com.example.tictactoerest.exceptions.GameStateException;
import com.example.tictactoerest.exceptions.IllegalMoveException;
import com.example.tictactoerest.exceptions.MissingGameException;
import com.example.tictactoerest.exceptions.MissingPlayerException;
import com.example.tictactoerest.repositories.IGameRepository;
import com.example.tictactoerest.repositories.IPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Contains business logic for starting and running
 * tictactoe games.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Service
public class GameService
{
    private IGameRepository gameRepo;
    private IPlayerRepository playerRepo;

    /**
     * Injects a data layer for games and players.
     *
     * @param gameRepo data layer for games
     * @param playerRepo data layer for players
     */
    public GameService(IGameRepository gameRepo, IPlayerRepository playerRepo)
    {
        this.gameRepo = gameRepo;
        this.playerRepo = playerRepo;
    }

    /**
     * Returns a player matching the input id.
     * @param playerId the player id
     * @throws MissingPlayerException if the player is not found
     * @return the player matching the id
     */
    public Player getPlayerById(int playerId)
    {
        Optional<Player> player = playerRepo.findById(playerId);
        if (player.isEmpty())
        {
            throw new MissingPlayerException(String.format("Player with id %s is missing", playerId));
        }
        return player.get();
    }

    /**
     * Returns whether the player is already in a game or not.
     *
     * @param player the player to search for
     * @return true if the player is in a match, otherwise false
     */
    public boolean alreadyInGame(Player player)
    {
        List<Game> games = gameRepo.findAllByPlayerXEqualsOrPlayerOEquals(player, player);
        for (Game game : games)
        {
            if (!game.isGameOver() && !game.isDraw())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Starts a new tictactoe game.
     *
     * @param playerXId the id of the player X
     * @param playerOId the id of the player O
     * @throws GameStateException if a player is already in a game
     * @return the new game object
     */
    public Game newGame(int playerXId, int playerOId)
    {
        //get the players if they exist
        Player playerX = getPlayerById(playerXId);
        Player playerO = getPlayerById(playerOId);

        //make sure they are not already in another game
        if (alreadyInGame(playerX))
        {
            throw new GameStateException(String.format("Player %s already in another game", playerX.getPlayerId()));
        }
        else if (alreadyInGame(playerO))
        {
            throw new GameStateException(String.format("Player %s already in another game", playerO.getPlayerId()));
        }

        //create the game and return it
        Game game = Game.builder()
                .gameState(new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '})
                .playerX(playerX)
                .playerO(playerO)
                .build();

        game = gameRepo.save(game);
        return game;
    }

    /**
     * Returns a list of all games (active or finished).
     *
     * @return a list of games
     */
    public List<Game> games()
    {
        return gameRepo.findAll();
    }

    /**
     * Returns a game that matches the input id.
     * @param gameId the game id
     * @throws MissingGameException if the game is not found
     * @return the game matching the input id
     */
    public Game getGameById(int gameId)
    {
        Optional<Game> game = gameRepo.findById(gameId);
        if (game.isEmpty())
        {
            throw new MissingGameException(String.format("Game with id %s missing", gameId));
        }
        return game.get();
    }

    /**
     * Makes a move in a game of tictactoe.
     *
     * @param gameId the id of the game
     * @param move the move made
     * @throws GameStateException if the game is already finished
     * @throws IllegalMoveException if one of the input players is not in this match
     * @return the updated game object
     */
    public Game move(int gameId, GameMove move)
    {
        Game game = getGameById(gameId);
        Player player = getPlayerById(move.getPlayerId());

        //preconditions
        if (game.isGameOver())
        {
            throw new GameStateException("You cannot make a move once the game is finished.");
        }
        else if (!game.isPlayerInMatch(player))
        {
            throw new IllegalMoveException(String.format("Player is not part of game with id %s", game.getGameId()));
        }
        else if (!game.isPlayerTurn(player))
        {
            throw new IllegalMoveException(String.format("It is not player %s's turn", player.getPlayerId()));
        }
        else
        {
            game.move(player, move.getRow(), move.getCol());

            //check for game status
            boolean gameDone = false;
            Player other = game.getOtherPlayer(player);
            if (game.isDraw())
            {
                gameDone = true;
                other.draw();
                player.draw();
            }
            else if (game.isGameOver())
            {
                gameDone = true;
                other.lose();
                player.win();
            }

            if (gameDone)
            {
                playerRepo.save(other);
                playerRepo.save(player);
            }
        }
        return gameRepo.save(game);
    }

    /**
     * Deletes a game that matches the input id.
     * @param gameId the game id to search for
     */
    public void deleteById(int gameId)
    {
        Optional<Game> savedGame = gameRepo.findById(gameId);
        if (savedGame.isEmpty())
        {
            throw new MissingGameException(String.format("Player with id %s missing", gameId));
        }
        gameRepo.deleteById(gameId);
    }

    @Override
    public String toString()
    {
        return "A games service managing " + gameRepo.count() + " games.";
    }
}












