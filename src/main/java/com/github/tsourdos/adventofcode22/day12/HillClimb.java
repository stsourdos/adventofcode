package com.github.tsourdos.adventofcode22.day12;

import java.util.*;
import java.util.stream.Collectors;

public class HillClimb {
    int x = 0;
    int y = 0;

    public void analyse(String input) {
        String[] split = input.split("\n");
        x = split[0].length();
        y = split.length;
        Pos[][] array = new Pos[y][x];
        Pos init = null;
        Pos end = null;
        for (int i = 0; i < y; i++) {
            String line = split[i];
            for (int j = 0; j < x; j++) {
                array[i][j] = new Pos(j, i, line.charAt(j));
                if (line.charAt(j) == 'S') {
                    init = array[i][j];
                }
                if (line.charAt(j) == 'E') {
                    end = array[i][j];
                }
            }
        }

        List<Route> routes = new ArrayList<>();
        routes.add(new Route());
        walk(array, routes, new Route(), Collections.singletonList(init));


        List<Route> res = new ArrayList<>();
        for (Route r : routes) {
            if (r.pos.contains(end)) {
                res.add(r);
            }
        }
        res.stream()
                .sorted(Comparator.comparing(r -> r.score))
                .forEach(System.out::println);
    }

    private void walk(Pos[][] array, List<Route> routes, Route route, List<Pos> allPos) {
        if (route.pos.contains(allPos.get(allPos.size() - 1))) {
            return;//we do cycles
        }
        allPos.forEach(route::add);
        if (!routes.contains(route)) {
            routes.add(route);
        }
        Pos cur = allPos.get(allPos.size() - 1);

        if (cur.c == 'E') {
            //System.out.println(route);
        } else {
            List<Pos> neighbors = getNeighbors(array, cur, route);
            for (Pos neighbor : neighbors) {
                List<Pos> objects = new ArrayList<>(route.pos);
                objects.add(neighbor);
                walk(array, routes, new Route(), objects);
            }
        }
    }

//    private void walk(Pos[][] array, List<Route> routes, Route route) {
//        Pos cur = route.pos.get(route.pos.size() - 1);
//        if (!routes.contains(route)) {
//            routes.add(route);
//        }
//
//        if (cur.c == 'E') {
//            // System.out.println(route);
//        } else {
//            List<Pos> neighbors = getNeighbors(array, cur, route);
//            List<Pos> parentPos = route.pos;
//            for (int i = 0; i < neighbors.size(); i++) {
//                Pos neighbor = neighbors.get(i);
//                if (!parentPos.contains(neighbor)) {
//                    if (i == 0) {
//                        route.add(neighbor);
//                        walk(array, routes, route);
//                    } else {
//                        Route newRoute = new Route();
//                        parentPos.forEach(newRoute::add);
//                        newRoute.add(neighbor);
//                        walk(array, routes, newRoute);
//                    }
//                }
//            }
//        }
//    }

    private List<Pos> getNeighbors(Pos[][] array, Pos cur, Route route) {
        List<Pos> res = new ArrayList<>();
        if (cur.y + 1 < y && isVisitable(cur.c, array[cur.y + 1][cur.x].c)) {
            res.add(array[cur.y + 1][cur.x]);//down
        }
        if (cur.y - 1 >= 0 && isVisitable(cur.c, array[cur.y - 1][cur.x].c)) {
            res.add(array[cur.y - 1][cur.x]);//up
        }
        if (cur.x + 1 < x && isVisitable(cur.c, array[cur.y][cur.x + 1].c)) {
            res.add(array[cur.y][cur.x + 1]);//right
        }
        if (cur.x - 1 >= 0 && isVisitable(cur.c, array[cur.y][cur.x - 1].c)) {
            res.add(array[cur.y][cur.x - 1]);
        }
        return res.stream().filter(re -> !route.pos.contains(re)).collect(Collectors.toList());
    }

    public boolean isVisitable(char cur, char target) {
        if (cur == 'S') {
            cur = 'a';
        }
        if (target == 'E') {
            target = 'z';
        }
        return target - cur == 1 || target - cur == 0;
    }


    public static class Route {
        List<Pos> pos = new ArrayList<>();
        int score = 0;

        void add(Pos posi) {
            if (!pos.contains(posi)) {
                pos.add(posi);
                score++;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Route route = (Route) o;
            return score == route.score && Objects.equals(pos, route.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, score);
        }

        @Override
        public String toString() {
            return "Route{" +
                    "pos=" + pos +
                    ", score=" + score +
                    '}';
        }
    }

    public static class Pos {
        int x;
        int y;
        Character c;

        public Pos(int x, int y, Character c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y && Objects.equals(c, pos.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, c);
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "x=" + x +
                    ", y=" + y +
                    ", c=" + c +
                    '}';
        }
    }
}
