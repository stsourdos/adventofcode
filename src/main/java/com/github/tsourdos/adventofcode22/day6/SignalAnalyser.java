package com.github.tsourdos.adventofcode22.day6;

public class SignalAnalyser {

    public int getStartOfPacketPosition (String input) {
        String signal = input.substring(0, 4);
        if (areAllCharsDifferent(signal)) {
            return 4;
        }
        for (int i=4; i<=input.length();i++) {
            signal = signal + input.charAt(i);
            signal = signal.substring(1);
            if (areAllCharsDifferent(signal)) {
                return i+1;
            }
        }
        return -1;
    }

    private boolean areAllCharsDifferent(String signal) {
        if (signal.length() != 4) {
            return false;
        }
        for (char letter : signal.toCharArray()) {
            long count = signal.chars().filter(ch -> ch == letter).count();
            if (count > 1) {
                return false;
            }
        }
        return true;
    }
}
