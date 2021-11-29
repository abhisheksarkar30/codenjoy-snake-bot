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
 * Author: your name
 *
 * This is your AI algorithm for the game.
 * Implement it at your own discretion.
 * Pay attention to {@see YourSolverTest} - there is
 * a test framework for you.
 */
public class YourSolver implements Solver<Board> {

    private final Dice dice;
    private Board board;
    //    private static List<Point> WALLS;
    private static Set<Point> visited = new HashSet<>();

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        Point head = board.getHead();
        if(head == null)
            return Direction.RIGHT.toString();
        Direction direction = board.getSnakeDirection();

        Point greenApple = board.getApples().get(0);

        Point nextAhead = head.copy();
        nextAhead.move(direction);
        Point nextLeftPt = head.copy();
        Direction nextLeftDir = direction.counterClockwise();
        nextLeftPt.move(nextLeftDir);
        Point nextRightPt = head.copy();
        Direction nextRightDir = direction.clockwise();
        nextRightPt.move(nextRightDir);

        double distAhead = calculateDistance(greenApple, nextAhead, direction);
        double distLeft = calculateDistance(greenApple, nextLeftPt, nextLeftDir);
        double distRight = calculateDistance(greenApple, nextRightPt, nextRightDir);

        Direction nextDirection = distAhead > distLeft? distLeft > distRight? direction.clockwise() :
                direction.counterClockwise() : distAhead > distRight? direction.clockwise() : direction;

        return nextDirection.toString();
    }

    private double calculateDistance(Point greenApple, Point head, Direction direction) {
        List<Point> barriers = board.getBarriers();
        List<Point> snakeBody = board.getSnake();
        barriers.addAll(snakeBody);

        if(barriers.contains(head))
            return 999999999;

        Point nextLeftPt = head.copy();
        Direction nextLeftDir = direction.counterClockwise();
        nextLeftPt.move(nextLeftDir);//board.getNextLeft(head,direction);
        Point nextRightPt = head.copy();
        Direction nextRightDir = direction.clockwise();
        nextRightPt.move(nextRightDir);//board.getNextRight(head, direction);

        if(!snakeBody.contains(nextLeftPt) || !snakeBody.contains(nextRightPt))
            return greenApple.distance(head);
        else
            return 999999998;
    }

}