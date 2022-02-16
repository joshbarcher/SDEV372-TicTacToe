package com.example.tictactoerest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point to the Web API application.
 *
 * @author Josh Archer
 * @version 1.0
 */
@SpringBootApplication
public class TicTacToeRestApplication
{
    /**
     * Starts the Spring Boot application
     * @param args command-line args
     */
    public static void main(String[] args)
    {
        SpringApplication.run(TicTacToeRestApplication.class, args);
    }

}
