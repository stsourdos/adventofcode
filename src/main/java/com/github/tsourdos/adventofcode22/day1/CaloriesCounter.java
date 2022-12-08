package com.github.tsourdos.adventofcode22.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaloriesCounter {

    public void analyse(String input) {
        long max = -1;
        long sum = 0;
        List<Long> cals = new ArrayList<>();
        String[] split = input.split("\n");
        for (int i = 0; i < split.length; i++) {
            String line = split[i];
            if (line.trim().isEmpty() || i == split.length - 1) {
                if (sum > max) {
                    max = sum;
                }
                if (i == split.length - 1) {
                    sum = sum + Long.parseLong(line);
                }
                cals.add(sum);
                sum = 0;
            } else {
                sum = sum + Long.parseLong(line);
            }
        }
        System.out.println(max);
        System.out.println(cals.stream().sorted().skip(cals.size() - 3).mapToLong(Long::longValue).sum());
    }
}
