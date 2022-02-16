package com.example.tictactoerest.exceptions;

/**
 * Represents a missing Game object in a game of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class MissingGameException extends RuntimeException
{
    /**
     * Passes a message along to the Exception object
     * @param message the error message
     */
    public MissingGameException(String message)
    {
        super(message);
    }
}
