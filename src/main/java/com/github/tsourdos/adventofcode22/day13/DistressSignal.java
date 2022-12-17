package com.github.tsourdos.adventofcode22.day13;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DistressSignal {

    public void analyse(String input) {
        List<Entry> init = new ArrayList<>();
        String[] lines = input.split("\n");
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            ListEntry listEntry = new ListEntry();
            init.add(listEntry);
            readLine(line, listEntry);
            if (!line.equals(listEntry.toString())) {
                System.out.println(line);
                System.out.println(listEntry);
            }
        }

        System.out.println("------ Part-1 -------");
        int index = 1;
        int res = 0;
        int res1 = 0;
        int res2 = 0;
        for (int i = 0; i < init.size(); i = i + 2) {
            Entry e1 = init.get(i);
            Entry e2 = init.get(i + 1);
            int rightOrder = compareEntries(e1, e2);
            if (rightOrder == 1) {
                res = res + index;
                res2++;
            }
            if (rightOrder == -1) {
                res1++;
            }
            index++;
        }
        System.out.println("Result:                 " + res); // 6272
        System.out.println("Not in the right order: " + res1);
        System.out.println("In the right order:     " + res2);

        System.out.println("------ Part-2 -------");
        addDividerPackets(init, 2);
        addDividerPackets(init, 6);
        init.sort(new CustomComparator());
        int index2 = 0;
        int index6 = 0;
        for (int i = 1; i <= init.size(); i++) {
            Entry entry = init.get(i - 1);
            if (entry.toString().equals("[[2]]")) {
                index2 = i;
            } else if (entry.toString().equals("[[6]]")) {
                index6 = i;
            }
        }
        System.out.println(index2 * index6); // 22288
    }

    public static class CustomComparator implements Comparator<Entry> {
        /**
         * Returns:
         * a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
         */
        @Override
        public int compare(Entry o1, Entry o2) {
            int res = compareEntries(o1, o2);
            if (res == 1) {
                return -1;
            }
            return 1;
        }
    }

    private static void addDividerPackets(List<Entry> init, int i) {
        ListEntry listEntry = new ListEntry();
        ListEntry listEntryIn = new ListEntry();
        listEntryIn.value.add(new IntEntry(i));
        listEntry.value.add(listEntryIn);
        init.add(listEntry);
    }

    /**
     * @return -1 false, 0 finish, 1 true
     */
    private static int compareEntries(Entry e1, Entry e2) {
        if (e1 instanceof IntEntry && e2 instanceof IntEntry) {
            Integer value1 = ((IntEntry) e1).value;
            Integer value2 = ((IntEntry) e2).value;
            if (value1.equals(value2)) {
                return 0;
            }
            if (value1 < value2) {
                return 1;
            }
            return -1;
        } else if (e1 instanceof ListEntry && e2 instanceof ListEntry) {
            List<Entry> value1 = ((ListEntry) e1).value;
            List<Entry> value2 = ((ListEntry) e2).value;
            if (value1.isEmpty() && value2.isEmpty()) {
                return 0;
            }
            for (int i = 0; i < value1.size(); i++) {
                if (value2.size() <= i) {
                    return -1;
                }
                Entry subEntry1 = value1.get(i);
                Entry subEntry2 = value2.get(i);
                int compare = compareEntries(subEntry1, subEntry2);
                if (compare != 0) {
                    return compare;
                }
            }
        } else {
            if (e1 instanceof IntEntry) {
                ListEntry listEntry = new ListEntry();
                listEntry.value.add(e1);
                return compareEntries(listEntry, e2);
            }
            if (e2 instanceof IntEntry) {
                ListEntry listEntry = new ListEntry();
                listEntry.value.add(e2);
                return compareEntries(e1, listEntry);
            }
        }
        return 1;
    }

    private static void readLine(String line, ListEntry entry) {
        if (line.startsWith("[")) {
            if (line.equals("[]")) {
                return;
            } else {
                line = line.substring(1, line.length() - 1);
            }
        }
        List<String> split = splitEntries(line);
        for (String str : split) {
            if (str.startsWith("[")) {
                ListEntry listEntry = new ListEntry();
                entry.value.add(listEntry);
                readLine(str, listEntry);
            } else {
                entry.value.add(new IntEntry(Integer.parseInt(str)));
            }
        }
    }

    private static List<String> splitEntries(String line) {
        List<String> list = new ArrayList<>();
        int startCount = 0;
        int startPos = 0;
        int endPos = 0;
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '[') {
                if (startCount == 0) {
                    startPos = i;
                }
                startCount++;
            } else if (c == ']') {
                startCount--;
                if (startCount == 0) {
                    endPos = i;
                    String substring = line.substring(startPos, endPos + 1);
                    if (!substring.isEmpty()) {
                        list.add(substring);
                    }
                }
            } else if (c != ',') {
                if (startCount == 0) {
                    tmp.append(c);
                }
            } else {
                if (startCount == 0 && (tmp.length() > 0)) {
                    list.add(tmp.toString());
                    tmp = new StringBuilder();
                }
            }
        }
        if (startCount == 0 && (tmp.length() > 0)) {
            list.add(tmp.toString());
        }
        return list;
    }


    public static class ListEntry extends Entry {
        List<Entry> value = new ArrayList<>();

        @Override
        public String toString() {
            return "[" + StringUtils.join(value, ", ").replaceAll(" ", "") + ']';
        }
    }

    public static class IntEntry extends Entry {
        Integer value;

        public IntEntry(Integer value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntEntry intEntry = (IntEntry) o;
            return Objects.equals(value, intEntry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static abstract class Entry {

    }
}
