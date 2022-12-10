package com.github.tsourdos.adventofcode22.day3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Rucksack {
    public void analyse(String input) {
        char[] alphabet = ("abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".toUpperCase()).toCharArray();
        Map<Character, Integer> prio = new HashMap<>();
        for (int i = 1; i <= 52; i++) {
            prio.put(alphabet[i - 1], i);
        }

        String[] split = input.split("\n");
        Map<Character, Integer> map = new HashMap<>();
        for (String line : split) {
            String ruck1 = line.substring(0, line.length() / 2);
            String ruck2 = line.substring(line.length() / 2);
            for (int i = 0; i < ruck1.length(); i++) {
                char c = ruck1.charAt(i);
                if (ruck2.contains(String.valueOf(c))) {
                    map.compute(c, (k, v) -> v == null ? prio.get(c) : v + prio.get(c));
                    break;
                }
            }
        }
        System.out.println(map.values().stream().mapToInt(Integer::intValue).sum());//7811

        int sum = 0;
        for (int i = 0; i < split.length; i = i + 3) {
            sum = sum + prio.get(getCommonChar(split[i], split[i + 1], split[i + 2]));
        }
        System.out.println(sum);
    }

    private Character getCommonChar(String str1, String str2, String str3) {
        Set<Character> common = new HashSet<>();
        Set<Character> common1 = new HashSet<>();
        for (int i = 0; i < str1.length(); i++) {
            char c = str1.charAt(i);
            if (str2.contains(String.valueOf(c))) {
                common.add(c);
            }
        }
        for (char c : str3.toCharArray()) {
            if (common.contains(c)) {
                common1.add(c);
            }
        }
        return common1.stream().findFirst().orElse('?');
    }
}
