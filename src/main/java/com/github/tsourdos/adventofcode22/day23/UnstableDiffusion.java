package com.github.tsourdos.adventofcode22.day23;

import java.util.*;

import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.U;
import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.D;
import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.L;
import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.R;

public class UnstableDiffusion {

    public static void main(String[] args) {
        String input = "....#..\n" +
                "..###.#\n" +
                "#...#.#\n" +
                ".#...##\n" +
                "#.###..\n" +
                "##.#.##\n" +
                ".#..#..";
        input = "...##..#.##.#..####....###..#.#..#.###.######.###...#...#.....###..##.#\n" +
                "..##.##.######.#.###.#.......#...####..####.###...#....#.##..#.#####.##\n" +
                "###.#.###.#.#..#.#.#..#.#.#####.#....#..###..#..#......####.#..####..##\n" +
                ".##...#.#..#.#..#.#.#.#....#.####...#..#..##.#...##.#..#.#.#.#......###\n" +
                "##..#.#.#.#.#.##.##.#.#...#.###.#...####...##..##..#.###.##.######..#.#\n" +
                "#.#.#....##.###....#.......######.....##.####.#.###...###.#.#...#####..\n" +
                ".##.....######.#......###.###.....#####...#..#####.#.#.##....#.#....##.\n" +
                "#...##.#....#.#.###.##.#..###.#...##...#.##.##.#..#.####.#.#.#.#.#...#.\n" +
                ".#.#.####.#...#..####.#...##.#.#.###.##..###..#.#.#.####.#.....#.##..#.\n" +
                ".#..#.#..##..######..#.##.##.##.#....#..#..#.##..###.#.#....###......#.\n" +
                ".####...#.##..##....#...#####.##.###......#.......#..##.#....###.......\n" +
                ".....#.#####...####.###..#.#.###.#..#.......#..........##.#####.#...#..\n" +
                ".....##..####...#.#...#.###...#..##.###......#.#..#....##...####....##.\n" +
                ".####..#.#.####.##.##..###..#...#.#.##.#..####.....####.#.#.##...#.....\n" +
                "..#..#..##..#.#....##..#####...#...#####....#....#..###.###.####.#...#.\n" +
                ".###.#.#####.###...#.#.##.##..###.#.##.##.####.###..#.......##...###...\n" +
                "####.#.#....#.###.#.##..###.#.##..##.#..##..###.####...###....#####..##\n" +
                ".##.#####..#.#.#.#...#......###.###.#....####.....#..#......##...###...\n" +
                "#.##.###.####.##.#..#.#...#..##..##....####.#..#..####...#.##.##.#.#..#\n" +
                ".....##.#####..#..#.##.......#######..##.##.....#..###...#####.....##..\n" +
                "..#..###...####.#.#.......###...##..#.######...#....#...#..##...###...#\n" +
                "##.#...###..#.###.##...#.#..#.#.##..#.###..#..#.####......##.#.#.####..\n" +
                ".###...###..#.#.##.###.##..##.#.#...###....#..##.#..###..##..####.#..#.\n" +
                "#..#.#....#...#.###.#....##..#....##.#...#.....#..#......#.#...#.#.###.\n" +
                ".##.#....##.##..###..#.#..####.###...##.#.##.####..#.####..#..##.##....\n" +
                "#.#.####..#...#.#.####....#..###..#..##....##...#.#.#.#.##.#...#.###...\n" +
                "######..#.#####.###..##.#####.#..#.#.#.#.......##.#.#..#....##.#.#...##\n" +
                "####....##.#..###....###.....#..#...##.##...#......#..##..#.#...####..#\n" +
                ".##..#####.#..##..##...#.#.#..#....#.####..##.......#.##.#.###.#.#..##.\n" +
                ".##...####..#..##..##.###....#....#..##.#.#.##......#.#.##...##.#####..\n" +
                "....#..###.##..##...###.##.##...#....#..####.#####.###..#.###.#.#.#..##\n" +
                "##.#.#.#.#...#######..#.##..#.#.#.####.#.###...##.######.##.....##.#..#\n" +
                "...###..#.##.#..#.###.....#####.##.##.#.##.#.#..##..###.#........#.#.##\n" +
                ".#....#.#.#..####....#.###.#.#.#..##..####..#..#.#...####..#..#..#.##..\n" +
                "##...#.#.##.##.#.###.#.#...######.###.....####.##..######..#..##.......\n" +
                "#.##.#.###..#....##....#.##.##...#####.##.######.....##.#...#.##..#.###\n" +
                "#..#....##.###..####..#..##.###....#..###....########...#..##.######...\n" +
                "##..####..##.#.###..#..####.##.##......##...###.##.###.###..#.###.#####\n" +
                "##.#.#..#.#.###.....#.#.#..####.#.##########...##.#.#.###.#.##..#.##.#.\n" +
                "#.##########.#.#.#..#######....#..##.#.###..##.....#.#..##..###.#.....#\n" +
                "#.##.##..##.##.########..#.#.###.#..##...#######.######.#.#.###..#..##.\n" +
                "...#.#..###...#.#..#.#.#.######...####.#..######.######...#.#...#.#.##.\n" +
                "#######.####.###.....##.#..#####.#....####....##.#.##..##.##..#....#.#.\n" +
                ".#.#.#..##.#.#....##.###.......##...#..##..####....####.#.#.#.###..##..\n" +
                ".#..####.#...#..#.##...#####.##..##.###......#.#..#.#.#.#..####.#.##..#\n" +
                ".###.#.####.####.####..#..#..#.#...#...#####....##.#..#.#####.##..###..\n" +
                ".#..#.##..#...##.##......#.#.#......##.#..#...####...##.#.##.#.#.####..\n" +
                "###.#.##.#.#########..##.#..##.#.#..#.#.#...##.##.#.#...#.#####.......#\n" +
                "..###...#..#..#.##.#.#.#.###....##..#..###.#.#.#........#.#...#..##.#.#\n" +
                ".###....##.########..##.#.##..#.##...##.###.#...#..#..##..##...##...#.#\n" +
                "##...#..##.#.#.##.#.#....##....###.##...###.#####..##.....##........#.#\n" +
                "..#...#.######..#.....#.#.##.#.#.###..##...##.#..#..#.#......#.##..#...\n" +
                "#...........#..##.####.###.#.....##....##.#.##..#...#..#####...##...#.#\n" +
                "....#..####...##..###..#####.#..####.###..#.##..###..#.#.##.#.########.\n" +
                "..#.#......####..#.##..#.###.##.#.###..#..#.###.#....#.##.##..#..##..#.\n" +
                "####.##.#.###.#.#######.###.......##..####.#.....##...##...###..###..#.\n" +
                "#....#..####.#..#.#.#.###.##........##.#..##.#..#.#..##..#...##.#...##.\n" +
                "#...###.###.#.#..##......#..###..##.#....###...#...##..#..#####.####.#.\n" +
                "######..####...####.##.....###..#...###.##......#..#.##.##.###.##.####.\n" +
                "..##......#......##.####..#..###....#..#..#.....#..##.....###.####..#.#\n" +
                "#####.###..##.....#....#...##.#.##...#.#.#.#..##.#..#...#..####...##...\n" +
                ".##..#.#...##..##..#.###.#.#.#...#####..#.####..###..##....##.####..#..\n" +
                "##...#####....#.#.##.###.#.#..##.#...#.#.#.####.....#.#..##.#...#......\n" +
                "#......###.###.###......##.####.#.####...##..#.#.#......######.#.#.#..#\n" +
                "..##.###.##.#.#.###.#.###.####..#..###########.##.##.#..#......##.#.###\n" +
                "..#.###....##.....###......##.##...##.#..###..#..#.#..###....######.##.\n" +
                "#####.#####.#.#.####...#########....#.....#...##..#.....###..###.######\n" +
                ".##.#####.#....#.#.#......##..##.#####...#.####.#.##..#..##..####...#..\n" +
                "####.##.###.#..#...##...##.#..##.....#..##..#####...........##..#..###.\n" +
                "##.##.####.#.#.....#.#.#..#..###..###.........#..#.#..####.##........##\n" +
                ".#.###......#...#..##.##...#..#..#.#..#.##.#.#.####..###.....#.###.#..#";
        new UnstableDiffusion().analyse(input);
    }

    public void analyse(String input) {
        String[] split = input.split("\n");
        Map<Integer, Pos> init = new HashMap<>();
        int elf = 0;
        for (int i = 0; i < split.length; i++) {
            String line = split[i];
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == '#') {
                    init.put(elf, new Pos(j, i));
                    elf++;
                }
            }
        }
        for (int round = 1; round <= Integer.MAX_VALUE; round++) {
            List<Dir> dirs = getDirections(round);
            Map<Integer, Pos> proposed = round1(init, dirs);
            if (proposed.isEmpty()) {
                System.out.println("Part-2: " + round);
                break;
            }
            for (Map.Entry<Integer, Pos> e : init.entrySet()) {
                if (proposed.containsKey(e.getKey()))
                    init.put(e.getKey(), proposed.get(e.getKey()));
            }
        }
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Pos pos : init.values()) {
            if (pos.x > maxX)
                maxX = pos.x;
            if (pos.x < minX)
                minX = pos.x;
            if (pos.y > maxY)
                maxY = pos.y;
            if (pos.y < minY)
                minY = pos.y;
        }
        int distX = maxX - minX + 1;
        int distY = maxY - minY + 1;
        System.out.println("Part-1: " + ((distY * distX) - init.size()));
    }

    private List<Dir> getDirections(int round) {
        if (round % 4 == 1)
            return Arrays.asList(U, D, L, R);
        if (round % 4 == 2)
            return Arrays.asList(D, L, R, U);
        if (round % 4 == 3)
            return Arrays.asList(L, R, U, D);
        return Arrays.asList(R, U, D, L);
    }

    private Map<Integer, Pos> round1(Map<Integer, Pos> init, List<Dir> dirs) {
        Map<Integer, Pos> proposed = new HashMap<>();
        for (Map.Entry<Integer, Pos> pos : init.entrySet()) {
            if (noNeighbor(init.values(), pos.getValue()))
                continue;
            for (Dir dir : dirs) {
                if (!hasNeighbor(init.values(), pos.getValue(), dir)) {
                    Pos newPos = new Pos(pos.getValue()).move(dir);
                    if (proposed.containsValue(newPos)) {
                        Integer key = getKeyByValue(proposed, newPos);
                        proposed.remove(key);
                    } else
                        proposed.put(pos.getKey(), newPos);
                    break;
                }
            }
        }
        return proposed;
    }

    private boolean noNeighbor(Collection<Pos> init, Pos pos) {
        return !init.contains(new Pos(pos.x, pos.y - 1)) &&
                !init.contains(new Pos(pos.x - 1, pos.y - 1)) &&
                !init.contains(new Pos(pos.x + 1, pos.y - 1)) &&
                !init.contains(new Pos(pos.x, pos.y + 1)) &&
                !init.contains(new Pos(pos.x - 1, pos.y + 1)) &&
                !init.contains(new Pos(pos.x + 1, pos.y + 1)) &&
                !init.contains(new Pos(pos.x - 1, pos.y)) &&
                !init.contains(new Pos(pos.x + 1, pos.y));

    }

    private boolean hasNeighbor(Collection<Pos> init, Pos pos, Dir dir) {
        if (dir.equals(U))
            return init.contains(new Pos(pos.x, pos.y - 1)) || init.contains(new Pos(pos.x - 1, pos.y - 1)) || init.contains(new Pos(pos.x + 1, pos.y - 1));
        if (dir.equals(D))
            return init.contains(new Pos(pos.x, pos.y + 1)) || init.contains(new Pos(pos.x - 1, pos.y + 1)) || init.contains(new Pos(pos.x + 1, pos.y + 1));
        if (dir.equals(L))
            return init.contains(new Pos(pos.x - 1, pos.y)) || init.contains(new Pos(pos.x - 1, pos.y - 1)) || init.contains(new Pos(pos.x - 1, pos.y + 1));
        if (dir.equals(R))
            return init.contains(new Pos(pos.x + 1, pos.y)) || init.contains(new Pos(pos.x + 1, pos.y - 1)) || init.contains(new Pos(pos.x + 1, pos.y + 1));
        throw new IllegalArgumentException("Invalid dir " + dir);
    }

    public static enum Dir {
        U, D, L, R
    }

    public static class Pos {
        int x, y;

        public Pos(Pos pos) {
            this.x = pos.x;
            this.y = pos.y;
        }

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pos up() {
            y--;
            return this;
        }

        public Pos down() {
            y++;
            return this;
        }

        public Pos left() {
            x--;
            return this;
        }

        public Pos right() {
            x++;
            return this;
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

        public Pos move(Dir dir) {
            if (dir == U)
                return up();
            if (dir == D)
                return down();
            if (dir == L)
                return left();
            if (dir == R)
                return right();
            throw new IllegalArgumentException("Bad arg");
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
