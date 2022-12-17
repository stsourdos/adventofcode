package com.github.tsourdos.adventofcode22.day15;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Beacons {
    Pattern pattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

//    int Y = 2000000;
        int Y = 10;
    Set<Point> beacons = new HashSet<>();
    Map<Integer, Set<Point>> sensors = new HashMap<>();

    public void analyse(String input) {
        String[] lines = input.split("\n");
        Set<Map.Entry<Point, Point>> in = new HashSet<>();

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Point s = new Point(parseInt(matcher.group(1)), parseInt(matcher.group(2)));
                Point b = new Point(parseInt(matcher.group(3)), parseInt(matcher.group(4)));
                beacons.add(b);
                sensors.compute(s.y,(k,v)->{
                    if (v==null) {
                        Set<Point> objects = new HashSet<>();
                        objects.add(s);
                        return objects;
                    }
                    v.add(s);
                    return v;
                });
                if (!line.equals(String.format("Sensor at x=%s, y=%s: closest beacon is at x=%s, y=%s", s.x, s.y, b.x, b.y))) {
                    throw new IllegalArgumentException("Error parsing: " + line);
                }
                in.add(new AbstractMap.SimpleEntry<>(s, b));
            } else {
                throw new IllegalArgumentException("cannot parse: " + line);
            }
        }

//        Set<Point> res = new HashSet<>();
//        for (Map.Entry<Point, Point> e : in) {
//            res.addAll(getCoveredPoints1(e.getKey(), getManhattan(e.getKey(), e.getValue())));
//            System.out.println(e);
//        }
        Set<Point> res = new HashSet<>();
        for (int y=0;y<=4000000;y++) {
            Y = y;
            for (Map.Entry<Point, Point> e : in) {
                int manhattan = getManhattan(e.getKey(), e.getValue());
                res.addAll(getCoveredPoints1(e.getKey(), manhattan));
            }
            if (sensors.containsKey(Y)) {
                res.addAll(sensors.get(Y));
            }
            if(res.size()!=4000001) {
                break;
            }
            System.out.println(y);
            res = new HashSet<>();
        }
        for (int x=0;x<=4000000;x++) {
            if (!res.contains(new Point(x,Y))) {
                System.out.println(new Point(x,Y));
            }
        }
    }

    private Set<Point> getCoveredPoints1(Point s, int manhattan) {
        Set<Point> covered = new HashSet<>();
        if (s.y == Y) {
            for (int i = 1; i <= manhattan; i++) {
                Point right = new Point(s.x + i, s.y);
                add(covered, right);
                Point left = new Point(s.x - i, s.y);
                add(covered, left);
            }
        } else if (s.y > Y) {
            int i = s.y - Y;
            int j = manhattan - i;
            if (manhattan>=i) {
                Point up = new Point(s.x, s.y - i);
                add(covered, up);
                for (int a = 1; a <= j; a++) {
                    add(covered, new Point(up).moveHorizontal(a));//right
                    add(covered, new Point(up).moveHorizontal(-a));//left
                }
            }
        } else {
            int i = Y - s.y;
            int j = manhattan - i;
            if (manhattan>=i) {
                Point down = new Point(s.x, s.y + i);
                add(covered, down);
                for (int a = 1; a <= j; a++) {
                    add(covered, new Point(down).moveHorizontal(a));//right
                    add(covered, new Point(down).moveHorizontal(-a));//left
                }
            }
        }
        return covered;
    }

    private boolean add(Set<Point> covered, Point point) {
//        if (point.y == Y && !beacons.contains(point)) {
        if (point.y == Y && point.x>=0&&point.x<=4000000) {
            return covered.add(point);
        }
        return false;
    }

    private int getManhattan(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(Point point) {
            this.x = point.x;
            this.y = point.y;
        }

        public Point moveVertical(int i) {
            this.y = this.y + i;
            return this;
        }

        public Point moveHorizontal(int i) {
            this.x = this.x + i;
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
