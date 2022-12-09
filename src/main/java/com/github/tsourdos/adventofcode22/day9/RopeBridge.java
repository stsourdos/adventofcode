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
            Position newPos = getTailPosition(move1, move(move1, headPos), headPos, tailPos, visited, true);
            headPos = move(move1, headPos);
            if (!newPos.equals(tailPos)) {
                tailPos = newPos;
            }
        }
        tailPos = new Position(0, 0);
        headPos = new Position(0, 0);
        System.out.println(visited.size());
        visited = new HashSet<>();
        visited.add(tailPos);
        Map<Integer, Position> map = new HashMap<>();
        map.put(1, new Position(0,0));
        map.put(2, new Position(0,0));
        map.put(3, new Position(0,0));
        map.put(4, new Position(0,0));
        map.put(5, new Position(0,0));
        map.put(6, new Position(0,0));
        map.put(7, new Position(0,0));
        map.put(8, new Position(0,0));
        map.put(9, new Position(0,0));
        map.put(10, new Position(0,0));
        for (String move : moves) {
            String[] s = move.split(" ");
            Move move1 = new Move(Direction.valueOf(s[0]), Integer.parseInt(s[1]));
            for (int i=1;i<10;i++) {
                Position head = map.get(i);
                Position tail = map.get(i+1);
                Position newPos = getTailPosition(move1, head, head, tail, visited, i==9);
                head = move(move1, head);
                if (i==1)
                    map.put(i, head);
                if (!newPos.equals(tail)) {
                    tail = newPos;
                    map.put(i+1,tail);
                }
            }
        }
        System.out.println(visited.size());
    }

    private Position getTailPosition(Move move, Position head, Position headInit, Position tail, Set<Position> visited, boolean b) {
        if (!isTouching(head, tail)) {
            Position targetPosition = null;
            if (move.d.equals(U)) {
                targetPosition = new Position(head.x, head.y - 1);
            } else if (move.d.equals(D)) {
                targetPosition = new Position(head.x, head.y + 1);
            } else if (move.d.equals(L)) {
                targetPosition = new Position(head.x + 1, head.y);
            } else if (move.d.equals(R)) {
                targetPosition = new Position(head.x - 1, head.y);
            }
            populateVisitedPositions(visited, headInit, tail, move, b);
            return targetPosition;
        }
        return tail;
    }

    private void populateVisitedPositions(Set<Position> visited, Position headInit, Position tail, Move move, boolean b) {
        Position newTail = tail;
        Position newHead = headInit;
        for (int i = 0; i < move.steps; i++) {
            newHead = move(new Move(move.d, 1), newHead);
            if (!isTouching(newHead, newTail) && needDiagonal(newHead, newTail, move)) {
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
                    if(b)
                        visited.add(newTail);
                }
            }
        }
    }

    private boolean needDiagonal(Position newHead, Position newTail, Move move) {
        Position tmpTail = move(new Move(move.d, 1), newTail);
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
