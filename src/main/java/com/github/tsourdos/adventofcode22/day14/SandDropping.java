package com.github.tsourdos.adventofcode22.day14;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class SandDropping {
    private static final int yMax = 600;
    private static final int xMax = 1000;
    private static final char EMPTY = '\u0000';

    public void analyse(String input) {
        String[] split = input.split("\n");
        char[][] array = new char[yMax][xMax];
        int maxY = 0;
        for (String line : split) {
            String[] points = line.split("->");
            List<Point> p = Arrays.stream(points)
                    .map(s -> new Point(parseInt(s.split(",")[0].trim()), parseInt(s.split(",")[1].trim())))
                    .collect(Collectors.toList());
            for (int i = 0; i < p.size() - 1; i++) {
                Point p1 = p.get(i);
                Point p2 = p.get(i + 1);
                List<Integer> coordsY = Stream.of(p1.y, p2.y).sorted().collect(Collectors.toList());
                List<Integer> coordsX = Stream.of(p1.x, p2.x).sorted().collect(Collectors.toList());
                if (coordsY.get(1) > maxY) {
                    maxY = coordsY.get(1);
                }
                for (int a = coordsY.get(0); a <= coordsY.get(1); a++) {
                    for (int b = coordsX.get(0); b <= coordsX.get(1); b++) {
                        array[a][b] = '#';
                    }
                }
            }
        }
        System.out.println("Part-1: " + simulate(array));

        populateHorLine(array, maxY);
        removeSand(array);
        System.out.println("Part-2: " + simulate(array));
    }

    private void removeSand(char[][] array) {
        for (int i = 0; i < yMax; i++) {
            for (int j = 0; j < xMax; j++) {
                if (array[i][j] == 'o') {
                    array[i][j] = EMPTY;
                }
            }
        }
    }

    private int simulate(char[][] array) {
        int res = 0;
        boolean repeat = true;
        Point sandGen = new Point(500, 0);
        while (repeat) {
            Point sandPos = new Point(500, 0);
            int i = 1;
            while (i < yMax) {
                Point next = new Point(sandPos).down();
                if (next.y >= yMax || next.x >= xMax) {
                    repeat = false;
                    break;
                }
                char c = get(array, next);
                if (c != EMPTY) {
                    Point downLeft = new Point(sandPos).downLeft();
                    Point downRight = new Point(sandPos).downRight();
                    if (get(array, downLeft) == EMPTY) {
                        sandPos.downLeft();
                    } else if (get(array, downRight) == EMPTY) {
                        sandPos.downRight();
                    } else {
                        res++;
                        draw(array, sandPos);
                        if (sandPos.equals(sandGen)) {
                            repeat = false;
                        }
                        break;
                    }
                } else {
                    sandPos.down();
                    i++;
                }
            }
        }
        // print(array, true);
        return res;
    }

    private void populateHorLine(char[][] array, int maxY) {
        for (int i = 0; i < xMax; i++) {
            array[maxY + 2][i] = '#';
        }
    }

    private char get(char[][] array, Point point) {
        return array[point.y][point.x];
    }

    private void draw(char[][] array, Point sandPos) {
        array[sandPos.y][sandPos.x] = 'o';
    }

    private void print(char[][] array, boolean small) {
        int aFrom = 0;
        int aTo = 200;
        int bFrom = 385;
        int bTo = 650;
        if (small) {
            aTo = 15;
            bFrom = 490;
            bTo = 515;
        }
        for (int a = aFrom; a <= aTo; a++) {
            System.out.println();
            for (int b = bFrom; b <= bTo; b++) {
                if (array[a][b] == EMPTY) {
                    System.out.print('.');
                } else {
                    System.out.print(array[a][b]);
                }
            }
        }
        System.out.println();
    }

    public class Point {
        int x, y;

        public Point(Point point) {
            this.x = point.x;
            this.y = point.y;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point down() {
            this.y++;
            return this;
        }

        public Point downLeft() {
            down();
            this.x--;
            return this;
        }

        public Point downRight() {
            down();
            this.x++;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
