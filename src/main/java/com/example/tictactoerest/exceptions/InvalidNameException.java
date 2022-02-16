package com.example.tictactoerest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents an invalid name for a player in a game of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidNameException extends RuntimeException
{
    /**
     * Passes a message along to the Exception object
     * @param message the error message
     */
    public InvalidNameException(String message)
    {
        super(message);
    }
}
