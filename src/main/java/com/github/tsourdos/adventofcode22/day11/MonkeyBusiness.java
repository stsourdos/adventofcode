package com.github.tsourdos.adventofcode22.day11;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

public class MonkeyBusiness {
    public void analyse(String input) {
        String[] split = input.split("\n");
        List<Monkey> monkeys = new ArrayList<>();
        int id = -1;
        Queue<String> q = new LinkedList<>();
        Function<String, String> f = null;
        int div = -1;
        int trueTargetMonkey = -1;
        int falseTargetMonkey = -1;
        Map<Integer, Integer> res = new HashMap<>();
        for (String line : split) {
            line = line.trim();
            if (line.isEmpty()) {
                monkeys.add(new Monkey(id, q, f, div, trueTargetMonkey, falseTargetMonkey));
                q = new LinkedList<>();
                res.put(id, 0);
                continue;
            }

            if (line.startsWith("Monkey ")) {
                id = parseInt(line.split(" ")[1].split(":")[0]);
            }
            if (line.startsWith("Starting items: ")) {
                Arrays.stream(line.split(": ")[1].split(","))
                        .map(String::trim)
                        //.map(BigInteger::new)
                        .forEach(q::offer);
            }
            if (line.startsWith("Operation: new = old")) {
                String fun = line.split("Operation: new = old")[1].trim();
                String secondArg = fun.split(" ")[1];
                if (fun.startsWith("*")) {
                    if (secondArg.equals("old")) {
                        f = in -> new BigInteger(in).pow(2).toString();
                    } else {
                        f = in -> multiply(in, secondArg);
                    }
                }
                if (fun.startsWith("+")) {
                    if (secondArg.equals("old")) {
                        f = in -> multiply(in, "2");
                    } else {
                        f = in -> add(secondArg, in);
                    }
                }
            }
            if (line.startsWith("Test: divisible by")) {
                div = parseInt(line.split("Test: divisible by")[1].trim());
            }
            if (line.startsWith("If true: throw to monkey")) {
                trueTargetMonkey = parseInt(line.split("If true: throw to monkey")[1].trim());
            }
            if (line.startsWith("If false: throw to monkey")) {
                falseTargetMonkey = parseInt(line.split("If false: throw to monkey")[1].trim());
            }
        }
        monkeys.add(new Monkey(id, q, f, div, trueTargetMonkey, falseTargetMonkey));
        res.put(id, 0);
        System.out.println(monkeys);

        int monkey = 0;
        int round = 1;
//        while (true) {
//            if (monkey == monkeys.size()) {
//                System.out.println("Round " + round);
//                monkey = 0;
//                round++;
//                if (round == 21) {
//                    break;
//                }
//            }
//            Monkey curMonkey = monkeys.get(monkey);
//            int size = curMonkey.q.size();
//            for (int i = 0; i < size; i++) {
//                BigDecimal item = curMonkey.q.poll();
//                BigDecimal itemWithWorry = curMonkey.f.apply(item);
//                itemWithWorry = itemWithWorry.divide(THREE, RoundingMode.FLOOR);
//                res.put(curMonkey.id, 1 + res.get(curMonkey.id));
//                if (itemWithWorry.remainder(new BigDecimal(curMonkey.div)).compareTo(BigDecimal.ZERO) == 0) {
//                    monkeys.get(curMonkey.trueTargetMonkey).q.offer(itemWithWorry);
//                } else {
//                    monkeys.get(curMonkey.falseTargetMonkey).q.offer(itemWithWorry);
//                }
//            }
//            monkey++;
//        }
        while (true) {
            if (monkey == monkeys.size()) {
                System.out.println("Round " + round);
                monkey = 0;
                round++;
                if (round == 10001) {
                    break;
                }
            }
            Monkey curMonkey = monkeys.get(monkey);
            int size = curMonkey.q.size();
            for (int i = 0; i < size; i++) {

                String item = curMonkey.q.poll();
                long t1=System.currentTimeMillis();
                String itemWithWorry = curMonkey.f.apply(item);
                long t2=System.currentTimeMillis();
                if (t2-t1>1000)
                    System.out.println("Took" + (t2-t1));
                res.put(curMonkey.id, 1 + res.get(curMonkey.id));
                if (mod(itemWithWorry, curMonkey.div)==0) {
                    monkeys.get(curMonkey.trueTargetMonkey).q.offer(itemWithWorry);
                } else {
                    monkeys.get(curMonkey.falseTargetMonkey).q.offer(itemWithWorry);
                }
            }
            monkey++;
        }
        System.out.println(res.values().stream().sorted().skip(res.size() - 2).mapToLong(Long::valueOf).reduce(1, (a, b) -> a * b));
    }

    public String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        if (len1 == 0 || len2 == 0)
            return "0";

        // will keep the result number in vector
        // in reverse order
        int result[] = new int[len1 + len2];

        // Below two indexes are used to
        // find positions in result.
        int i_n1 = 0;
        int i_n2 = 0;

        // Go from right to left in num1
        for (int i = len1 - 1; i >= 0; i--) {
            int carry = 0;
            int n1 = num1.charAt(i) - '0';

            // To shift position to left after every
            // multipliccharAtion of a digit in num2
            i_n2 = 0;

            // Go from right to left in num2
            for (int j = len2 - 1; j >= 0; j--) {
                // Take current digit of second number
                int n2 = num2.charAt(j) - '0';

                // Multiply with current digit of first number
                // and add result to previously stored result
                // charAt current position.
                int sum = n1 * n2 + result[i_n1 + i_n2] + carry;

                // Carry for next itercharAtion
                carry = sum / 10;

                // Store result
                result[i_n1 + i_n2] = sum % 10;

                i_n2++;
            }

            // store carry in next cell
            if (carry > 0)
                result[i_n1 + i_n2] += carry;

            // To shift position to left after every
            // multipliccharAtion of a digit in num1.
            i_n1++;
        }

        // ignore '0's from the right
        int i = result.length - 1;
        while (i >= 0 && result[i] == 0)
            i--;

        // If all were '0's - means either both
        // or one of num1 or num2 were '0'
        if (i == -1)
            return "0";

        // genercharAte the result String
        String s = "";

        while (i >= 0)
            s += (result[i--]);

        return s;
    }

    public int mod(String num, int a) {
        // Initialize result
        int res = 0;

        // One by one process all digits of 'num'
        for (int i = 0; i < num.length(); i++)
            res = (res * 10 + (int) num.charAt(i) - '0') % a;

        return res;
    }

    public static class Monkey {
        int id;
        Queue<String> q;
        Function<String, String> f;
        int div;
        int trueTargetMonkey;
        int falseTargetMonkey;

        public Monkey(int id, Queue<String> q, Function<String, String> f, int div, int trueTargetMonkey, int falseTargetMonkey) {
            this.id = id;
            this.q = q;
            this.f = f;
            this.div = div;
            this.trueTargetMonkey = trueTargetMonkey;
            this.falseTargetMonkey = falseTargetMonkey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Monkey monkey = (Monkey) o;
            return id == monkey.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Monkey{" +
                    "id=" + id +
                    ", q=" + q +
                    ", div=" + div +
                    ", trueTargetMonkey=" + trueTargetMonkey +
                    ", falseTargetMonkey=" + falseTargetMonkey +
                    '}';
        }
    }

    public String add(String a, String b) {
        int i = a.length();
        int j = b.length();
        int k = Math.max(i, j) + 1; // room for carryover
        char[] c = new char[k];
        for (int digit = 0; k > 0; digit /= 10) {
            if (i > 0)
                digit += a.charAt(--i) - '0';
            if (j > 0)
                digit += b.charAt(--j) - '0';
            c[--k] = (char) ('0' + digit % 10);
        }
        for (k = 0; k < c.length - 1 && c[k] == '0'; k++) {/*Skip leading zeroes*/}
        return new String(c, k, c.length - k);
    }
}
