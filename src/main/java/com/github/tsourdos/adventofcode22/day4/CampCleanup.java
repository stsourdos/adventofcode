package com.github.tsourdos.adventofcode22.day4;

import java.util.HashSet;
import java.util.Set;

public class CampCleanup {
    public void analyse(String input) {
        String[] pairs = input.split("\n");
        int sum = 0;
        int sum2 = 0;
        for (String pair : pairs) {
            String[] assignments = pair.split(",");
            Set<Integer> sections1 = assignmentToSections(assignments[0]);
            Set<Integer> sections2 = assignmentToSections(assignments[1]);
            if (sections1.containsAll(sections2)||sections2.containsAll(sections1)) {
                sum++;
            }
            if (sections1.stream().anyMatch(sections2::contains)||sections2.stream().anyMatch(sections1::contains)) {
                sum2++;
            }
        }
        System.out.println(sum);
        System.out.println(sum2);
    }

    private Set<Integer> assignmentToSections(String assignment) {
        String[] split = assignment.split("-");
        int from = Integer.parseInt(split[0]);
        int to = Integer.parseInt(split[1]);
        Set<Integer> sections = new HashSet<>();
        for (int i=from;i<=to;i++) {
            sections.add(i);
        }
        return sections;
    }

}
