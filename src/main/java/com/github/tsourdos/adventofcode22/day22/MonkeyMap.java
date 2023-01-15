package com.github.tsourdos.adventofcode22.day22;

import java.util.*;

/**
 * 143206 too high
 */
public class MonkeyMap {
    private static final char EMPTY = '\u0000';

    public void analyse(String input) {
        String[] lines = input.split("\n");
        int max = -1;
        for (String line : lines)
            if (line.length() > max)
                max = line.length();
        Point[][] map = new Point[lines.length - 2][max];
        Point position = new Point(-1, 0, EMPTY);
        for (int i = 0; i < lines.length - 2; i++) {
            String line = lines[i];
            for (int j = 0; j < max; j++) {
                if (line.length() > j) {
                    map[i][j] = new Point(i, j, line.charAt(j));
                    if (i == 0 && position.x == -1 && line.charAt(j) == '.')
                        position = new Point(j, i, line.charAt(j));
                } else
                    map[i][j] = new Point(i, j, EMPTY);
            }
        }
        print(map);
        System.out.println("Init position" + position);

        String path = lines[lines.length - 1];
        List<Move> moves = new ArrayList<>();
        char direction = 'R';
        String steps = "";
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (i != 0 && (c == 'R' || c == 'L' || c == 'U' || c == 'D')) {
                moves.add(new Move(Integer.parseInt(steps), direction));
                steps = "";
                direction = c;
            } else {
                steps = steps + c;
            }
        }
        moves.add(new Move(Integer.parseInt(steps), direction));
        StringBuilder readPath = new StringBuilder();
        for (Move m : moves)
            readPath.append(m);
        if (!readPath.toString().equals("R" + path))
            throw new IllegalArgumentException("input misread " + readPath);

        char dir = 'R';
        for (int j = 0; j < moves.size(); j++) {
            Move move = moves.get(j);
            dir = j == 0 ? 'R' : getDirection(dir, move.direction);
            for (int i = 0; i < move.steps; i++) {
                boolean moved = false;
                if (dir == 'R')
                    moved = position.right(map);
                if (dir == 'L')
                    moved = position.left(map);
                if (dir == 'U')
                    moved = position.up(map);
                if (dir == 'D')
                    moved = position.down(map);

                if (!moved) break;
            }
            System.out.println(dir + "" + move.steps + " " + position);
        }
        int res = 1000*(position.y-1) + 4*(position.x-1) +getDirectionScore(dir);
        System.out.println("Part-1: " + res);
    }

    private int getDirectionScore(char dir) {
        if (dir == 'R') return 0;
        if (dir == 'D') return 1;
        if (dir == 'L') return 2;
        if (dir == 'U') return 3;
        throw new IllegalArgumentException("Wrong direction " + dir);
    }

    private char getDirection(char dir, char direction) {
        if (dir == 'R' && direction == 'R') return 'D';
        if (dir == 'D' && direction == 'R') return 'L';
        if (dir == 'L' && direction == 'R') return 'U';
        if (dir == 'U' && direction == 'R') return 'R';
        if (dir == 'R' && direction == 'L') return 'U';
        if (dir == 'D' && direction == 'L') return 'R';
        if (dir == 'L' && direction == 'L') return 'D';
        if (dir == 'U' && direction == 'L') return 'L';
        throw new IllegalArgumentException("Wrong direction " + dir + direction);
    }

    private void print(Point[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].c != EMPTY)
                    System.out.print(map[i][j].c);
            }
            System.out.println();
        }
    }

    public class Move {
        int steps;
        char direction;

        public Move(int steps, char direction) {
            this.steps = steps;
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return steps == move.steps && direction == move.direction;
        }

        @Override
        public String toString() {
            return direction + "" + steps;
        }

        @Override
        public int hashCode() {
            return Objects.hash(steps, direction);
        }
    }

    public class Point {
        int x, y;
        char c;

        public Point(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        public boolean right(Point[][] map) {
            if (x < map[y].length - 1 && map[y][x + 1].c != EMPTY) {
                if (map[y][x + 1].c == '.') {
                    x++;
                    return true;
                }
                return false;
            }
            for (int i = 0; i < map[y].length; i++) {
                if (map[y][i].c == EMPTY)
                    continue;
                if (map[y][i].c == '#')
                    return false;
                if (map[y][i].c == '.') {
                    x = i;
                    return true;
                }
            }
            return false;
        }

        public boolean down(Point[][] map) {
            if (y < map.length - 1 && map[y + 1][x].c != EMPTY) {
                if (map[y + 1][x].c == '.') {
                    y++;
                    return true;
                }
                return false;
            }
            for (int i = 0; i < map.length; i++) {
                if (map[i][x].c == EMPTY)
                    continue;
                if (map[i][x].c == '#')
                    return false;
                if (map[y][i].c == '.') {
                    y = i;
                    return true;
                }
            }
            return false;
        }

        public boolean left(Point[][] map) {
            if (x > 0 && map[y][x - 1].c != EMPTY) {
                if (map[y][x - 1].c == '.') {
                    x--;
                    return true;
                }
                return false;
            }
            for (int i = map[y].length - 1; i > -1; i--) {
                if (map[y][i].c == EMPTY)
                    continue;
                if (map[y][i].c == '#')
                    return false;
                if (map[y][i].c == '.') {
                    x = i;
                    return true;
                }
            }
            return false;
        }

        public boolean up(Point[][] map) {
            if (y > 0 && map[y - 1][x].c != EMPTY) {
                if (map[y - 1][x].c == '.') {
                    y--;
                    return true;
                }
                return false;
            }
            for (int i = map.length - 1; i > -1; i--) {
                if (map[i][x].c == EMPTY)
                    continue;
                if (map[i][x].c == '#')
                    return false;
                if (map[i][x].c == '.') {
                    y = i;
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && c == point.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, c);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", c=" + c +
                    '}';
        }
    }
}
