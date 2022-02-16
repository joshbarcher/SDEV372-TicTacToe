package com.example.tictactoerest.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents a Player in a game of tictactoe.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;
    private String name;

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    /**
     * Increments the wins for the player.
     */
    public void win()
    {
        wins++;
    }

    /**
     * Increments the losses for the player.
     */
    public void lose()
    {
        losses++;
    }

    /**
     * Increments the draws for the player.
     */
    public void draw()
    {
        draws++;
    }
}
