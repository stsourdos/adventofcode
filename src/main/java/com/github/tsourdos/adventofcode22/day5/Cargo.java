package com.github.tsourdos.adventofcode22.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Cargo {
    String pattern = "move (\\d+) from (\\d+) to (\\d+)";
    Pattern r = Pattern.compile(pattern);

    public void analyse(String input) {
        String[] split = input.split("\n");
        List<Move> moves = new ArrayList<>();
        List<String> init = new ArrayList<>();
        int max = 0;
        for (String line : split) {
            if (line.contains("[") || line.contains("   ")) {
                init.add(line);
                if (line.length() > max) {
                    max = line.length();
                }
            } else if (line.contains("move")) {
                Matcher m = r.matcher(line);
                if (m.find()) {
                    moves.add(new Move(parseInt(m.group(2)), parseInt(m.group(3)), parseInt(m.group(1))));
                }
            }
        }

        List<Stack<Character>> cargo = new ArrayList<>();
        for (Character chara : init.get(init.size() - 1).toCharArray()) {
            if (!chara.equals(' ')) {
                cargo.add(new Stack<>());
            }
        }
        Character[][] in = new Character[init.size()][max];
        for (int i = 0; i < init.size(); i++) {
            String line = init.get(i);
            for (int j = 0; j < max; j++) {
                if (j >= line.length()) {
                    in[i][j] = ' ';
                } else {
                    in[i][j] = line.charAt(j);
                }
            }
        }
        for (int i = 0; i < max; i++) {
            Character c = in[init.size() - 1][i];
            if (!c.equals(' ')) {
                for (int j = init.size() - 2; j >= 0; j--) {
                    Character character = in[j][i];
                    if (!character.equals(' ')) {
                        cargo.get(parseInt(c.toString()) - 1).push(character);
                    }
                }
            }
        }

//        for (Move move : moves) {
//            for (int i = 1; i <= move.quant; i++) {
//                Character peek = cargo.get(move.from - 1).pop();
//                cargo.get(move.to - 1).push(peek);
//            }
//        }
//        String res = "";
//        for (Stack<Character> carg : cargo) {
//            res = res + carg.peek();
//        }
//        System.out.println(res);

        for (Move move : moves) {
            List<Character> peeks = new ArrayList<>();
            for (int i = 1; i <= move.quant; i++) {
                peeks.add(cargo.get(move.from - 1).pop());
            }
            for (int i = peeks.size()-1; i >=0; i--) {
                cargo.get(move.to - 1).push(peeks.get(i));
            }
        }
        String res = "";
        for (Stack<Character> carg : cargo) {
            res = res + carg.peek();
        }
        System.out.println(res);
    }

    public class Move {
        int from;
        int to;
        int quant;

        public Move(int from, int to, int quant) {
            this.from = from;
            this.to = to;
            this.quant = quant;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return from == move.from && to == move.to && quant == move.quant;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, quant);
        }

        @Override
        public String toString() {
            return "Move{" +
                    "from=" + from +
                    ", to=" + to +
                    ", quant=" + quant +
                    '}';
        }
    }
}
