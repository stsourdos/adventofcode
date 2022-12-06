package com.github.tsourdos.adventofcode22.day6;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SignalAnalyserTest {

    @Test
    public void testSignalPosition() {
        String content = readFile("src/test/resources/adventofcode/2022/day6/input.txt");
        System.out.println(new SignalAnalyser().getStartOfPacketPosition(content));
    }

    public static String readFile(String path) {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
