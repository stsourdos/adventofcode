package com.github.tsourdos.adventofcode22.day8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreeHouseHelper {

    public void analyse(String input) {
        String[] rows = input.split("\n");
        int x = rows.length;
        int y = rows[0].length();
        //  Character[][] forest = new Character[x][y];
        Map<Pos, Integer> forest = new HashMap<>();
        for (int i = 0; i < y; i++) {
            char[] chars = rows[i].toCharArray();
            for (int j = 0; j < x; j++) {
                forest.put(new Pos(j, i), toInt(chars[j]));
            }
        }
//        System.out.println(forest);
        boolean[][] visible = new boolean[x][y];
        int vis=0;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
                    visible[i][j] = true;
                    vis++;
                } else {
                    Boolean visible1 = isVisible(forest, i, j, x, y);
                    visible[i][j] = visible1;
                    if (visible1)
                        vis++;
                }
            }
        }
        System.out.println(vis);

        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {

                }else
                    points.add(getTreePoints(forest, i,j,x,y));
            }
        }
        System.out.println(points.stream().max(Comparator.naturalOrder()));
    }

    private int getTreePoints(Map<Pos, Integer> forest, int i, int j, List<Pos> positions) {
        int height = forest.get(new Pos(i, j));

        int points = 0;
        if (positions.isEmpty()||forest.get(positions.get(0))>=height) {
            return 1;
        }
        for (Pos pos:positions) {
            if (forest.get(pos)<height)
                points++;
            else
                return points + 1;
        }
        return points;
    }

    private Boolean isVisible(Map<Pos, Integer> forest, int i, int j, List<Pos> positions) {
        int height = forest.get(new Pos(i, j));
        for (Pos pos:positions) {
            if (forest.get(pos)>=height)
                return false;
        }
        return true;
    }
    private int getTreePoints(Map<Pos, Integer> forest, int i, int j, int x, int y) {
        return
            getTreePoints(forest, i, j, rev(0,i).boxed().map(ver-> new Pos(ver, j)).filter(forest::containsKey).collect(Collectors.toList()))*
            getTreePoints(forest, i, j, IntStream.range(i+1,x).boxed().map(ver-> new Pos(ver, j)).filter(forest::containsKey).collect(Collectors.toList()))*
            getTreePoints(forest, i, j, rev(0,j).boxed().map(hor-> new Pos(i, hor)).filter(forest::containsKey).collect(Collectors.toList()))*
            getTreePoints(forest, i, j, IntStream.range(j+1,y).boxed().map(hor-> new Pos(i, hor)).filter(forest::containsKey).collect(Collectors.toList()))
        ;
    }

    private IntStream rev(int from, int to) {
        return IntStream.range(from,to).map(i->to-i+from-1);
    }

    private Boolean isVisible(Map<Pos, Integer> forest, int i, int j, int x, int y) {
        return
            isVisible(forest,i,j,IntStream.range(0,i).boxed().map(ver-> new Pos(ver, j)).filter(forest::containsKey).collect(Collectors.toList())) ||
            isVisible(forest,i,j,IntStream.range(i+1,x).boxed().map(ver-> new Pos(ver, j)).filter(forest::containsKey).collect(Collectors.toList())) ||
            isVisible(forest,i,j,IntStream.range(0,j).boxed().map(hor-> new Pos(i, hor)).filter(forest::containsKey).collect(Collectors.toList())) ||
            isVisible(forest,i,j,IntStream.range(j+1,y).boxed().map(hor-> new Pos(i, hor)).filter(forest::containsKey).collect(Collectors.toList()))
        ;
    }

    private int toInt(Character character){
        return Character.getNumericValue(character);
    }

    public static class Pos {
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
