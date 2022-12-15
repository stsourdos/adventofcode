package com.github.tsourdos.adventofcode22.day14;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class SandDropping {

    public void analyse(String input) {
        String[] split = input.split("\n");
        char[][] array = new char[600][600];
        for (String line : split) {
            String[] points = line.split("->");
            List<Point> p = Arrays.stream(points)
                    .map(s -> new Point(parseInt(s.split(",")[0].trim()), parseInt(s.split(",")[1].trim())))
                    .collect(Collectors.toList());
            for (int i = 0; i < p.size() - 1; i++) {
                Point p1 = p.get(i);
                Point p2 = p.get(i + 1);
                for (int a = p1.x; a <= p2.x; a++) {
                    for (int b = p1.y; b <= p2.y; b++) {
                        array[a][b] = '#';
                    }
                }
            }
        }
        for (int a = 490; a <= 515; a++) {
            System.out.println();
            for (int b = 0; b <= 15; b++) {
                System.out.print(array[a][b]);
            }
        }
    }

    public class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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
