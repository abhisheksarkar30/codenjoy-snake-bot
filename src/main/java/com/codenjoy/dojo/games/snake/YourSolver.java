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
import com.codenjoy.dojo.services.PointImpl;
import com.google.common.collect.Lists;

import java.util.List;

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

    private List<Point> barriers, walls, stones, snakeBody;
    private Point greenApple;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        long startTime = System.currentTimeMillis();

        this.board = board;
        Point currentHead = board.getHead();
        if(currentHead == null)
            return Direction.RIGHT.toString();

        barriers = Lists.newArrayList();
        barriers.addAll(walls = board.getWalls());
        barriers.addAll(stones = board.getStones());
        barriers.addAll(snakeBody = board.getSnake());
        greenApple = board.getApples().get(0);
        Direction currentDir = board.getSnakeDirection();

        Direction nextDir = getNextDirection(currentHead, currentDir);

        System.out.println("Time taken(ms) : " + (System.currentTimeMillis() - startTime));

        return nextDir.toString();
    }

    private Direction getNextDirection(Point currentHead, Direction currentDir) {
        Point nextAhead = getNextHeadAhead(currentHead, currentDir);
        Direction nextLeftDir = currentDir.counterClockwise();
        Point nextLeft = getNextHeadLeft(currentHead, currentDir);
        Direction nextRightDir = currentDir.clockwise();
        Point nextRight = getNextHeadRight(currentHead, currentDir);

        double distAhead = calculateDistanceFromGreenApple(nextAhead, currentDir, false);
        double distLeft = calculateDistanceFromGreenApple(nextLeft, nextLeftDir, false);
        double distRight = calculateDistanceFromGreenApple(nextRight, nextRightDir, false);

        return distAhead > distLeft? distLeft > distRight? nextRightDir : nextLeftDir :
                distAhead > distRight? nextRightDir : currentDir;
    }

    private double calculateDistanceFromGreenApple(Point tempHead, Direction tempDir, boolean avoidStones) {
        if(!avoidStones && stones.contains(tempHead))
            return 999999996;//Left as one of the last options but better than going trapped inside own body
        else if(walls.contains(tempHead))
            return 999999998;//Directly blocked passage
        else if(snakeBody.contains(tempHead))
            return 999999999;//Directly blocked passage

        Point nextLeftPt = getNextHeadLeft(tempHead,tempDir);
        Point nextRightPt = getNextHeadRight(tempHead, tempDir);

        if(!snakeBody.contains(nextLeftPt) || !snakeBody.contains(nextRightPt))
            return greenApple.distance(tempHead);
        else {
            Point nextHead = getNextHeadAhead(tempHead, tempDir);
            if(!greenApple.itsMe(nextHead) &&
                    calculateDistanceFromGreenApple(nextHead, tempDir, true) == 999999999)
                return 999999999;
        }

        return greenApple.distance(tempHead);
    }

    public Point getNextHeadAhead(Point currentHead, Direction direction) {
        Point nextHead;
        switch(direction) {
            case LEFT : nextHead = new PointImpl(currentHead.getX()-1, currentHead.getY()); break;
            case RIGHT : nextHead = new PointImpl(currentHead.getX()+1, currentHead.getY()); break;
            case UP : nextHead = new PointImpl(currentHead.getX(), currentHead.getY()+1); break;
            case DOWN : nextHead = new PointImpl(currentHead.getX(), currentHead.getY()-1); break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return nextHead;
    }

    public Point getNextHeadLeft(Point currentHead, Direction direction) {
        Point nextHead;
        switch(direction) {
            case LEFT : nextHead = new PointImpl(currentHead.getX(), currentHead.getY()-1); break;
            case RIGHT : nextHead = new PointImpl(currentHead.getX(), currentHead.getY()+1); break;
            case UP : nextHead = new PointImpl(currentHead.getX()-1, currentHead.getY()); break;
            case DOWN : nextHead = new PointImpl(currentHead.getX()+1, currentHead.getY()); break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return nextHead;
    }

    public Point getNextHeadRight(Point currentHead, Direction direction) {
        Point nextHead;
        switch(direction) {
            case LEFT : nextHead = new PointImpl(currentHead.getX(), currentHead.getY()+1); break;
            case RIGHT : nextHead = new PointImpl(currentHead.getX(), currentHead.getY()-1); break;
            case UP : nextHead = new PointImpl(currentHead.getX()+1, currentHead.getY()); break;
            case DOWN : nextHead = new PointImpl(currentHead.getX()-1, currentHead.getY()); break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return nextHead;
    }

}