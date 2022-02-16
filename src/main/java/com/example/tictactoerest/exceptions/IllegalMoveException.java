package com.example.tictactoerest.exceptions;

/**
 * Represents a bad move in a game of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class IllegalMoveException extends RuntimeException
{
    /**
     * Passes a message along to the Exception object
     * @param message the error message
     */
    public IllegalMoveException(String message)
    {
        super(message);
    }
}
