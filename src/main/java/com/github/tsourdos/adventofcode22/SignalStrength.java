package com.github.tsourdos.adventofcode22;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SignalStrength {
    private static final List<Integer> targetCycles = Arrays.asList(20, 60, 100, 140, 180, 220);

    public void analyse(String input) {
        int cycle = 0;
        int subcycle = 0;
        int X = 1;
        String[] split = input.split("\n");
        int res = 0;
        Character[] res2 = new Character[1000];
        for (String cmd : split) {
            if (cmd.startsWith("addx")) {
                for (int i = 0; i < 2; i++) {
                    cycle++;
                    subcycle++;
                    if (subcycle == 41) {
                        subcycle = 1;
                    }
                    res2[cycle - 1] = isOverlapping(subcycle - 1, X);

                    if (targetCycles.contains(cycle)) {
                        res = res + (cycle * X);
                    }
                    if (i == 1) {
                        X = X + parseInt(cmd.split(" ")[1]);
                    }
                }
            } else {
                cycle++;
                subcycle++;
                if (subcycle == 41) {
                    subcycle = 1;
                }
                res2[cycle - 1] = isOverlapping(subcycle - 1, X);
                if (targetCycles.contains(cycle)) {
                    res = res + (cycle * X);
                }
            }
        }
        System.out.println(res);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res2.length; i++) {
            if (i % 40 == 0) {
                sb.append("\n");
            }
            if (res2[i] != null) {
                sb.append(res2[i]);
            }
        }
        System.out.println(sb);
    }

    private Character isOverlapping(int cycle, int x) {
        if (cycle == x || cycle == x - 1 || cycle == x + 1) {
            return '#';
        }
        return '.';
    }
}
