package com.github.tsourdos.adventofcode22.day15;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Beacons {
    Pattern pattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

        int Y = 2000000;
//    int Y = 10;
//    int xyMax = 20;
    int xyMax = 4000000;
    Set<Point> beacons = new HashSet<>();
    Map<Integer, Set<Point>> sensors = new HashMap<>();
    Map<Point, Integer> sensorToManhattan = new HashMap<>();

    public void analyse(String input) {
        String[] lines = input.split("\n");
        Set<Map.Entry<Point, Point>> in = new HashSet<>();

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Point s = new Point(parseInt(matcher.group(1)), parseInt(matcher.group(2)));
                Point b = new Point(parseInt(matcher.group(3)), parseInt(matcher.group(4)));
                beacons.add(b);
                sensors.compute(s.y, (k, v) -> {
                    if (v == null) {
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
        for (Map.Entry<Point, Point> e : in) {
            int manhattan = getManhattan(e.getKey(), e.getValue());
//            res.addAll(getCoveredPoints1(e.getKey(), manhattan));
            sensorToManhattan.put(e.getKey(), manhattan);
        }
//        System.out.println("Part-1: " + res.size());

//        for (int i=0;i<=xyMax;i++) {
//            for (int j=0;j<=xyMax;j++) {
//                Point testPoint = new Point(j,i);
//                boolean covered = false;
//                for (Map.Entry<Point,Integer> e:sensorToManhattan.entrySet()) {
//                    int manhattan = getManhattan(e.getKey(), testPoint);
//                    if (manhattan<=e.getValue()) {
//                        covered = true;
//                        break;
//                    }
//                }
//                if (!covered) {
//                    System.out.println(testPoint);
//                }
//            }
//            if (i%200==0) {
//                System.out.println(i);
//            }
//        }

        List<Line> listP = sensorToManhattan.entrySet()
                .stream()
                .map(e-> Arrays.asList(
                        new Line(new Point(e.getKey()).moveHorizontal(e.getValue()),new Point(e.getKey()).moveVertical(-e.getValue())),
                        new Line(new Point(e.getKey()).moveHorizontal(-e.getValue()),new Point(e.getKey()).moveVertical(e.getValue()))
                ))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Line> listN = sensorToManhattan.entrySet()
                .stream()
                .map(e-> Arrays.asList(
                        new Line(new Point(e.getKey()).moveHorizontal(e.getValue()),new Point(e.getKey()).moveVertical(e.getValue())),
                        new Line(new Point(e.getKey()).moveHorizontal(-e.getValue()),new Point(e.getKey()).moveVertical(-e.getValue()))
                ))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        for (int i=0;i< listP.size();i++) {
            for (int j=i+1;j< listP.size();j++) {
                Line line1 = listP.get(i);
                Line line2 = listP.get(j);
                if (getManhattan(line1.a, line2.a)==2||getManhattan(line1.b, line2.b)==2||getManhattan(line1.a, line2.b)==2||getManhattan(line1.a, line2.b)==2) {
                    System.out.println(line1 + " "+ line2);
                }
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
            if (manhattan >= i) {
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
            if (manhattan >= i) {
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
        if (point.y == Y && !beacons.contains(point)) {
            return covered.add(point);
        }
        return false;
    }

    private int getManhattan(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public class Line {
        Point a,b;

        public Line(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return Objects.equals(a, line.a) && Objects.equals(b, line.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        @Override
        public String toString() {
            return "Line{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
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
