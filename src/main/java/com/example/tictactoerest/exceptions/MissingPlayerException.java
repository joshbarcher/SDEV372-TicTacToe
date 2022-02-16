package com.example.tictactoerest.exceptions;

/**
 * Represents a missing Player object in a game of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class MissingPlayerException extends RuntimeException
{
    /**
     * Passes a message along to the Exception object
     * @param message the error message
     */
    public MissingPlayerException(String message)
    {
        super(message);
    }
}
