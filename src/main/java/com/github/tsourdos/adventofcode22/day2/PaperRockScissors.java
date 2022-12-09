package com.github.tsourdos.adventofcode22.day2;

import java.util.HashMap;
import java.util.Map;

import static com.github.tsourdos.adventofcode22.day2.PaperRockScissors.ActionType.*;

public class PaperRockScissors {


    public void analyse(String input) {
        Map<ActionType, Integer> score = new HashMap<>();
        score.put(R, 1);
        score.put(P, 2);
        score.put(S, 3);
        String[] rounds = input.split("\n");
        int sum = 0;
        for (String round : rounds) {
            String[] roundDetails = round.split(" ");
            sum = sum + getPoints(roundDetails[0], roundDetails[1], score);
        }
        System.out.println(sum);
        sum = 0;
        for (String round : rounds) {
            String[] roundDetails = round.split(" ");
            String actionType = chooseAction(roundDetails[0], roundDetails[1]);
            sum = sum + getPoints(roundDetails[0], actionType, score);
        }
        System.out.println(sum);
    }

    private String chooseAction(String pl1, String result) {
        ActionType actionType1 = getActionType(pl1);

        if (result.equals("Y")) {
            return pl1;
        } else if (result.equals("Z")) {
            if (actionType1.equals(R)) {
                return "B";
            } else if (actionType1.equals(P)) {
                return "C";
            }
            return "A";
        } else if (result.equals("X")) {
            if (actionType1.equals(R)) {
                return "C";
            } else if (actionType1.equals(P)) {
                return "A";
            }
            return "B";
        }
        throw new IllegalArgumentException("Unknown use case " + result);
    }

    private int getPoints(String pl1, String pl2, Map<ActionType, Integer> score) {
        int score2 = 0;
        ActionType actionType1 = getActionType(pl1);
        ActionType actionType2 = getActionType(pl2);
        int score1 = score.get(actionType2);
        if (actionType2.equals(actionType1)) {
            score2 = 3;
        } else if ((actionType2.equals(P) && actionType1.equals(S)) ||
                ((actionType2.equals(S) && actionType1.equals(R))) ||
                ((actionType2.equals(R) && actionType1.equals(P)))
        ) {
            score2 = 0;
        } else {
            score2 = 6;
        }
        return score1 + score2;
    }

    private ActionType getActionType(String pl) {
        if (pl.equals("A") || pl.equals("X")) {
            return R;
        }
        if (pl.equals("B") || pl.equals("Y")) {
            return P;
        }
        return S;
    }

    public enum ActionType {
        R, P, S
    }
}
