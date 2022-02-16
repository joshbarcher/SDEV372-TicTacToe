package com.example.tictactoerest.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a single move on a tictactoe board.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameMove
{
    private int playerId;
    private int row;
    private int col;
}
