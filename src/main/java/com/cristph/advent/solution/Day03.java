package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day03 extends AbstractDaySolve {

    @Override
    protected String solvePuzzle1(String[] args) {
        String fileName = args[0];
        int j = 0;
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                int size = line.length();
                if (line.charAt(j % size) == '#') {
                    count += 1;
                }
                j = j + 3;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(count);
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        String fileName = args[0];
        int[][] slopes = new int[][]{{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        int[][] post = new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
        long[] count = new long[]{0L, 0L, 0L, 0L, 0L};

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                int size = line.length();
                for (int n = 0; n < slopes.length; n++) {
                    if (post[n][1] % slopes[n][1] == 0) {
                        if (line.charAt(post[n][0] % size) == '#') {
                            count[n] += 1;
                        }
                    }
                    post[n][0] = post[n][0] + slopes[n][0];
                    post[n][1] = post[n][1] + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(Arrays.stream(count).reduce(1L, (a, b) -> a * b));
    }
}
