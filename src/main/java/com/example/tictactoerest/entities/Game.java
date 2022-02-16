package com.example.tictactoerest.entities;

import com.example.tictactoerest.exceptions.IllegalMoveException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents a match of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game
{
    private static final int CELLS = 9;
    private static final int ROWS = 3;
    private static final int COLS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;
    private char[] gameState = new char[CELLS];

    @OneToOne
    private Player playerX;
    @OneToOne
    private Player playerO;

    private int turn = 0; //even = x, odd = o
    private boolean gameover = false; //cache the gameover status
    private boolean draw = false; //cache the draw status

    /**
     * Returns true if the input player is the next player
     * to place a token on the board.
     *
     * @param player the player to place a new token
     * @return true if the player is next, otherwise false
     */
    public boolean isPlayerTurn(Player player)
    {
        if (!isPlayerInMatch(player))
        {
            throw new IllegalMoveException(String.format("Player is not part of game with id %s", gameId));
        }

        if (turn % 2 == 0)
        {
            return isPlayerX(player);
        } else
        {
            return isPlayerO(player);
        }
    }

    /**
     * Is the match a draw?
     *
     * @return true if the match is finished and all
     *         spots are full, otherwise false
     */
    public boolean isDraw()
    {
        return draw;
    }

    /**
     * Is the match over?
     *
     * @return true if a winner has been found in the
     *         match, otherwise false.
     */
    public boolean isGameOver()
    {
        return gameover;
    }

    private boolean determineIfDraw()
    {
        for (int i = 0; i < gameState.length; i++)
        {
            if (gameState[i] == ' ')
            {
                return false;
            }
        }
        return true;
    }

    private boolean determineIfGameover()
    {
        return checkRows() || checkCols() || checkDiagonals();
    }

    private boolean checkRows()
    {
        for (int row = 0; row < ROWS; row++)
        {
            boolean rowEqual = true;
            for (int col = 1; col < COLS; col++)
            {
                //check for a solution across a row
                int index = row * COLS + col;
                if (gameState[index - 1] == ' ' || gameState[index] == ' ')
                {
                    rowEqual = false;
                    break;
                } else if (gameState[index - 1] != gameState[index])
                {
                    rowEqual = false;
                    break;
                }
            }

            if (rowEqual)
            {
                return rowEqual;
            }
        }
        return false;
    }

    private boolean checkCols()
    {
        for (int col = 0; col < COLS; col++)
        {
            boolean colEqual = true;
            for (int row = 1; row < ROWS; row++)
            {
                //check for a solution across a cols
                int index = row * COLS + col;
                if (gameState[index - ROWS] == ' ' || gameState[index] == ' ')
                {
                    colEqual = false;
                    break;
                } else if (gameState[index - ROWS] != gameState[index])
                {
                    colEqual = false;
                    break;
                }
            }

            if (colEqual)
            {
                return colEqual;
            }
        }
        return false;
    }

    private boolean checkDiagonals()
    {
        return (gameState[0] != ' ' &&
                gameState[0] == gameState[4] &&
                gameState[4] == gameState[8]) || (
                gameState[2] != ' ' &&
                        gameState[2] == gameState[4] &&
                        gameState[4] == gameState[6]);
    }

    /**
     * Returns true if the player is in this match.
     *
     * @param player the player to search for
     * @return true if the player is player X or O, otherwise false
     */
    public boolean isPlayerInMatch(Player player)
    {
        return playerX.equals(player) || playerO.equals(player);
    }

    /**
     * Returns true if this player is player X in the match.
     *
     * @param player the player to search for
     * @return true if the player is player X, otherwise false
     */
    public boolean isPlayerX(Player player)
    {
        return playerX.equals(player);
    }

    /**
     * Returns true if this player is player O in the match.
     *
     * @param player the player to search for
     * @return true if the player is player O, otherwise false
     */
    public boolean isPlayerO(Player player)
    {
        return playerO.equals(player);
    }

    /**
     * Allows a player to make a move in the match.
     *
     * @param player the player making the move
     * @param row the row to place a token on
     * @param col the column to place a token on
     */
    public void move(Player player, int row, int col)
    {
        int index = row * COLS + col;
        if (row < 0 || row >= 3 || col < 0 || col >= 3)
        {
            throw new IllegalMoveException(String.format("Bad row (%s) or column (%s)", row, col));
        }
        else if (gameState[index] != ' ')
        {
            throw new IllegalMoveException(String.format("Row %s, col %s, already occupied", row, col));
        }

        char token = isPlayerX(player) ? 'x' : 'o';
        gameState[index] = token;
        turn++;

        //save game state
        draw = determineIfDraw();
        gameover = determineIfGameover();
    }

    /**
     * Returns the other player that is in the match
     * with the input parameter.
     *
     * @param player one of the players in the match
     * @return the other player that is not the input parameter
     */
    public Player getOtherPlayer(Player player)
    {
        return player.equals(playerX) ? playerO : playerX;
    }
}
