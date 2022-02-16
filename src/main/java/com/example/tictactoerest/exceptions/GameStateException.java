package com.example.tictactoerest.exceptions;

/**
 * Represents a bad state in a game of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class GameStateException extends RuntimeException
{
    /**
     * Passes a message along to the Exception object
     * @param message the error message
     */
    public GameStateException(String message)
    {
        super(message);
    }
}
