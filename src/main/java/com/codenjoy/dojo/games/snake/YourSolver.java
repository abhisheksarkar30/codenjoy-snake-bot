package com.codenjoy.dojo.games.snake;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Abhishek Sarkar
 *
 * This is your AI algorithm for the game.
 * Implement it at your own discretion.
 * Pay attention to {@see YourSolverTest} - there is
 * a test framework for you.
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        Point currentHead = board.getHead();
        if(currentHead == null)
            return Direction.RIGHT.toString();
        Direction currentDir = board.getSnakeDirection();

        Point greenApple = board.getApples().get(0);

        Point nextAhead = board.getNextHeadAhead(currentHead,currentDir);
        Point nextLeft = board.getNextLeft(currentHead,currentDir);
        Point nextRight = board.getNextRight(currentHead, currentDir);

        double distAhead = calculateDistance(greenApple, nextAhead);
        double distLeft = calculateDistance(greenApple, nextLeft);
        double distRight = calculateDistance(greenApple, nextRight);

        Direction nextDir = distAhead > distLeft? distLeft > distRight? currentDir.clockwise() :
                currentDir.counterClockwise() : distAhead > distRight? currentDir.clockwise() : currentDir;

        return nextDir.toString();
    }

    private double calculateDistance(Point greenApple, Point tempHead) {
        if(board.getBarriers().contains(tempHead))
            return 999999999;

        return greenApple.distance(tempHead);
    }

}