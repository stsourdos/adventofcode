package com.github.tsourdos.adventofcode22.day9;

import java.util.*;

import static com.github.tsourdos.adventofcode22.day9.RopeBridge.Direction.*;

public class RopeBridge {

    public void analyse(String input) {
        String[] moves = input.split("\n");
        Position tailPos = new Position(0, 0);
        Position headPos = new Position(0, 0);
        Set<Position> visited = new HashSet<>();
        visited.add(tailPos);
        for (String move : moves) {
            String[] s = move.split(" ");
            Move move1 = new Move(Direction.valueOf(s[0]), Integer.parseInt(s[1]));
            tailPos = populateVisitedPositions(visited, headPos, move(move1, headPos), tailPos, move1, true, true);
            headPos = move(move1, headPos);
        }
        System.out.println(visited.size());


        visited = new HashSet<>();
        visited.add(new Position(0, 0));
        Map<Integer, Position> map = new HashMap<>();
        for (int i = 1; i < 11; i++) {
            map.put(i, new Position(0, 0));
        }
        boolean tailMoved = false;
        for (String move : moves) {
            String[] s = move.split(" ");
            Move move1 = new Move(Direction.valueOf(s[0]), Integer.parseInt(s[1]));
            for (int steps = 1; steps <= move1.steps; steps++) {
                for (int i = 1; i < 10; i++) {
                    Position head = map.get(i);
                    Position tail = map.get(i + 1);
                    Position headTarget = i == 1 ? move(new Move(move1.d, 1), head) : head;
                    Position newTail = populateVisitedPositions(visited, head, headTarget, tail, new Move(move1.d, 1), i == 9, tailMoved);
                    if(!map.get(i+1).equals(newTail)) {
                        tailMoved = true;
                        map.put(i + 1, newTail);
                    }
                    if (i == 1)
                        map.put(i, headTarget);
                }
            }
        }
        System.out.println(visited.size());
    }

    private Position populateVisitedPositions(Set<Position> visited, Position headInit, Position headTarget, Position tail, Move move, boolean b, boolean moved) {
        if (isTouching(headTarget, tail)) {
            return tail;
        }
        Position newTail = tail;
        Position newHead = headInit;
        for (int i = 0; i < move.steps; i++) {
            newHead = moved?move(new Move(move.d, 1), newHead):headInit;
            move=moved?move:new Move(U, 0);
            if (!isTouching(newHead, newTail) && needDiagonal(newHead, newTail, move, moved?1:0)) {
                if (move.d.equals(U)) {
                    newTail = new Position(newHead.x, newHead.y - 1);
                } else if (move.d.equals(D)) {
                    newTail = new Position(newHead.x, newHead.y + 1);
                } else if (move.d.equals(L)) {
                    newTail = new Position(newHead.x + 1, newHead.y);
                } else if (move.d.equals(R)) {
                    newTail = new Position(newHead.x - 1, newHead.y);
                }
                if (b)
                    visited.add(newTail);
            } else {
                if (!isTouching(newHead, newTail)) {
                    newTail = move(new Move(move.d, 1), newTail);
                    if (b)
                        visited.add(newTail);
                }
            }
        }
        return newTail;
    }

    private boolean needDiagonal(Position newHead, Position newTail, Move move, int steps) {
        Position tmpTail = move(new Move(move.d, steps), newTail);
        return newHead.x != tmpTail.x && newHead.y != tmpTail.y;
    }

    private boolean isTouching(Position head, Position tail) {
        return Math.abs(head.x - tail.x) <= 1 && Math.abs(head.y - tail.y) <= 1;
    }

    private Position move(Move move, Position pos) {
        if (move.d.equals(U)) {
            pos = new Position(pos.x, pos.y + move.steps);
        } else if (move.d.equals(D)) {
            pos = new Position(pos.x, pos.y - move.steps);
        } else if (move.d.equals(L)) {
            pos = new Position(pos.x - move.steps, pos.y);
        } else if (move.d.equals(R)) {
            pos = new Position(pos.x + move.steps, pos.y);
        }
        return pos;
    }

    public class Move {
        Direction d;
        int steps;

        public Move(Direction d, int steps) {
            this.d = d;
            this.steps = steps;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return steps == move.steps && d == move.d;
        }

        @Override
        public int hashCode() {
            return Objects.hash(d, steps);
        }

        @Override
        public String toString() {
            return "Move{" +
                    "d=" + d +
                    ", steps=" + steps +
                    '}';
        }
    }

    public enum Direction {
        U, D, L, R
    }

    public class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
