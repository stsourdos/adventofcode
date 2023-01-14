package com.github.tsourdos.adventofcode22.day21;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Part-1: -1463981878
 */
public class MonkeyMath {
    public void analyse(String input) {
        Map<String, BigInteger> known = new HashMap<>();
        Map<String, String> unknown = new HashMap<>();
        String[] split = input.split("\n");
        for (String line : split) {
            String[] parts = line.split(": ");
            if (parts[1].trim().contains(" "))
                unknown.put(parts[0].trim(), parts[1].trim());
            else
                known.put(parts[0].trim(), new BigInteger(parts[1].trim()));
        }
        int totalMonkeys = known.size() + unknown.size();
//        while (known.size() < totalMonkeys) {
//            for (Map.Entry<String, String> e : unknown.entrySet()) {
//                String operation = e.getValue();
//                String[] s = operation.trim().split(" ");
//                if (!known.containsKey(e.getKey())&&known.containsKey(s[0]) && known.containsKey(s[2]))
//                    known.put(e.getKey(), getOpResult(known.get(s[0]), s[1], known.get(s[2])));
//            }
//        }
//        System.out.println("Part-1: " + known.get("root"));

        String monkey1 = unknown.get("root").trim().split(" ")[0];
        String monkey2 = unknown.get("root").trim().split(" ")[2];
        known.remove("humn");
        while (known.size() < totalMonkeys-1) {
            for (Map.Entry<String, String> e : unknown.entrySet()) {
                if (e.getKey().equals("root")) continue;
                String operation = e.getValue();
                String[] s = operation.trim().split(" ");
                if (known.containsKey(s[0]) && known.containsKey(s[2])) {
                    known.put(e.getKey(), getOpResult(known.get(s[0]), s[1], known.get(s[2])));
                } else if (known.containsKey(s[0]) && known.containsKey(e.getKey())) {
                    known.put(s[2], getOpResult1(known.get(e.getKey()), s[1], known.get(s[0])));
                } else if (known.containsKey(s[2]) && known.containsKey(e.getKey())) {
                    known.put(s[0], getOpResult1(known.get(e.getKey()), s[1], known.get(s[2])));
                }
                if (known.containsKey(e.getKey())&& e.getKey().equals(monkey1))
                    known.put(monkey2, known.get(monkey1));
                if (known.containsKey(e.getKey())&& e.getKey().equals(monkey2))
                    known.put(monkey1, known.get(monkey2));
            }
        }
        //8966704899332 too high
        System.out.println("Part-2: " + known.get("humn"));
    }

    private BigInteger getOpResult1(BigInteger integer, String op, BigInteger integer1) {
        if (op.equals("+")) //
            return integer.subtract(integer1);
        if (op.equals("-"))
            return integer.add(integer1);
        if (op.equals("*"))
            return integer.divide(integer1);
        if (op.equals("/"))
            return integer.multiply(integer1);
        throw new IllegalArgumentException("Unknown op " + op);
    }

    private BigInteger getOpResult(BigInteger integer, String op, BigInteger integer1) {
        if (op.equals("+"))
            return integer.add(integer1);
        if (op.equals("-"))
            return integer.subtract(integer1);
        if (op.equals("*"))
            return integer.multiply(integer1);
        if (op.equals("/"))
            return integer.divide(integer1);
        throw new IllegalArgumentException("Unknown op " + op);
    }
}
