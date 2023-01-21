package com.github.tsourdos.adventofcode22.day23;

import java.util.*;

import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.U;
import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.D;
import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.L;
import static com.github.tsourdos.adventofcode22.day23.UnstableDiffusion.Dir.R;

public class UnstableDiffusion {

    public void analyse(String input) {
        String[] split = input.split("\n");
        Map<Integer,Pos> init = new HashMap();
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
        for (int round = 1; round <= 10; round++) {
            List<Dir> dirs = getDirections(round);
            List<Pos> proposed = round1(init, dirs);
        }
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

    private List<Pos> round1(Map<Integer, Pos> init, List<Dir> dirs) {
        List<Pos> proposed = new ArrayList<>();
        for (Map.Entry<Integer,Pos> pos : init.entrySet()) {
            if (noNeighbor(init.values(), pos.getValue()))
                continue;
            for (Dir dir : dirs) {
                if (!hasNeighbor(init.values(), pos.getValue(), dir)) {
                    // move
                }
            }
        }
        return proposed;
    }

    private boolean noNeighbor(Collection<Pos> init, Pos pos) {
        return !init.contains(new Pos(pos.x, pos.y - 1)) && !init.contains(new Pos(pos.x - 1, pos.y - 1)) && !init.contains(new Pos(pos.x + 1, pos.y - 1))
                && !init.contains(new Pos(pos.x, pos.y + 1)) && !init.contains(new Pos(pos.x - 1, pos.y + 1)) && !init.contains(new Pos(pos.x + 1, pos.y + 1))
                && !init.contains(new Pos(pos.x - 1, pos.y)) && !init.contains(new Pos(pos.x - 1, pos.y - 1)) && !init.contains(new Pos(pos.x - 1, pos.y + 1))
                && !init.contains(new Pos(pos.x + 1, pos.y)) && !init.contains(new Pos(pos.x + 1, pos.y - 1)) && !init.contains(new Pos(pos.x + 1, pos.y + 1));

    }

    private boolean hasNeighbor(Collection<Pos> init, Pos pos, Dir dir) {
        if (dir.equals(U))
            return init.contains(new Pos(pos.x, pos.y - 1)) && init.contains(new Pos(pos.x - 1, pos.y - 1)) && init.contains(new Pos(pos.x + 1, pos.y - 1));
        if (dir.equals(D))
            return init.contains(new Pos(pos.x, pos.y + 1)) && init.contains(new Pos(pos.x - 1, pos.y + 1)) && init.contains(new Pos(pos.x + 1, pos.y + 1));
        if (dir.equals(L))
            return init.contains(new Pos(pos.x - 1, pos.y)) && init.contains(new Pos(pos.x - 1, pos.y - 1)) && init.contains(new Pos(pos.x - 1, pos.y + 1));
        if (dir.equals(R))
            return init.contains(new Pos(pos.x + 1, pos.y)) && init.contains(new Pos(pos.x + 1, pos.y - 1)) && init.contains(new Pos(pos.x + 1, pos.y + 1));
        throw new IllegalArgumentException("Invalid dir " + dir);
    }

    public static enum Dir {
        U, D, L, R
    }

    public static class Pos {
        int x, y;

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
