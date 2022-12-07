package com.github.tsourdos.adventofcode22.day7;

import java.util.HashMap;
import java.util.Map;

import static com.github.tsourdos.adventofcode22.day7.DirectoriesAnalyser.LineType.*;

public class DirectoriesAnalyser {

    public void analyse (String input) {
        String[] lines = input.split("\n");
        Map<String,Long> dirToSize = new HashMap<>();
        String myDir = "";
        for (String line : lines) {
            LineType type = getLineType(line);
            if (FILE_DATA.equals(type)) {
                addFileSize(dirToSize,myDir,new Long(line.split(" ")[0]));
            } else if (CD_COMMAND_FW.equals(type)) {
                myDir = myDir+getDir(line.split(" ")[2]);
            } else if (CD_COMMAND_BACK.equals(type)) {
                myDir = getDir(myDir.substring(0,myDir.substring(0,myDir.length()-1).lastIndexOf("/")));
            }
        }
        System.out.println(dirToSize.entrySet()
                .stream()
                .filter(e->e.getValue()<100000)
                .mapToLong(Map.Entry::getValue)
                .sum());

        System.out.println(dirToSize);
        Long empty = 70000000-dirToSize.get("/");
        Long toFree = 30000000-empty;
        System.out.println(dirToSize.entrySet()
                .stream()
                .filter(e -> e.getValue() > toFree)
                .mapToLong(Map.Entry::getValue)
                .sorted()
                .findFirst()
                .orElse(0L));
    }

    private void addFileSize(Map<String, Long> dirToSize, String myDir, Long aLong) {
        if (dirToSize.containsKey(myDir)) {
            dirToSize.put(myDir,dirToSize.get(myDir)+aLong);
        }else {
            dirToSize.put(myDir,aLong);
        }
        if (!myDir.isEmpty() && !myDir.equals("/")) {
            String substring = myDir.substring(0, myDir.length() - 1);
            if (substring.length()==0)
                addFileSize(dirToSize,"/",aLong);
            else {
                addFileSize(dirToSize, getDir(substring.substring(0,substring.lastIndexOf("/"))), aLong);
            }
        }
    }

    private String getDir(String s) {
        if (s.endsWith("/")) {
            return s;
        }
        return s+"/";
    }

    private LineType getLineType(String line) {
        if (line.startsWith("$ cd ..")) {
            return LineType.CD_COMMAND_BACK;
        } else if (line.startsWith("$ cd")) {
            return LineType.CD_COMMAND_FW;
        } else if (line.startsWith("$ ls")) {
            return LineType.LS_COMMAND;
        } else if (line.startsWith("dir")) {
            return LineType.DIR_DATA;
        } else {
            return LineType.FILE_DATA;
        }
    }

    public static enum LineType{
        CD_COMMAND_FW,CD_COMMAND_BACK,LS_COMMAND,FILE_DATA,DIR_DATA
    }
}
