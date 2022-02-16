package com.example.tictactoerest.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the players in a new match.
 *
 * @author Josh Archer
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchUp
{
    private int playerXId;
    private int playerOId;
}
