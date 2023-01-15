package com.github.tsourdos.adventofcode22.day22;

import java.util.Objects;

public class MonkeyMap {
    private static final char EMPTY = '\u0000';

    public void analyse(String input) {
        String[] lines = input.split("\n");
        int max = -1;
        for (String line : lines)
            if (line.length() > max)
                max = line.length();
        Point[][] map = new Point[lines.length - 2][max];
        for (int i = 0; i < lines.length - 2; i++) {
            String line = lines[i];
            for (int j = 0; j < max; j++) {
                if (line.length() > j)
                    map[i][j] = new Point(i, j, line.charAt(j));
                else
                    map[i][j] = new Point(i, j, EMPTY);
            }
        }
        print(map);

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

    public class Point {
        int x, y;
        char c;

        public Point(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        public boolean right(Point[][] map) {
            if (x < map[y].length - 1) {
                if (map[y][x + 1].c != '#')
                    x++;
                return map[y][x + 1].c != '#';
            }
            for (int i = 0; i < map[y].length; i++) {
                if (map[y][i].c != EMPTY && map[y][i].c == '#')
                    return false;
                if (map[y][i].c != EMPTY && map[y][i].c == '.') {
                    x = i;
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
