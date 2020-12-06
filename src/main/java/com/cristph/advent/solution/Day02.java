package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * @author cristph
 * @date 2020/12/4 10:05 下午
 */
public class Day02 extends AbstractDaySolve {

    @Override
    protected String solvePuzzle1(String[] args) {
        return solvePuzzle(args, this::checkPassword);
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        return solvePuzzle(args, this::checkPassword2);
    }

    private String solvePuzzle(String[] args, Function<String, Integer> f) {
        String fileName = args[0];
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return String.valueOf(br.lines().map(f).reduce(0, Integer::sum));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer checkPassword(String s) {
        String[] tmp = s.split("\\s");
        String[] num_tmp = tmp[0].split("-");
        Integer min = Integer.parseInt(num_tmp[0]);
        Integer max = Integer.parseInt(num_tmp[1]);
        char c = tmp[1].charAt(0);
        String str = tmp[2];
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
                if (count > max) {
                    return 0;
                }
            }
        }
        if (count < min) {
            return 0;
        }
        return 1;
    }

    public Integer checkPassword2(String s) {
        String[] tmp = s.split("\\s");
        String[] num_tmp = tmp[0].split("-");
        Integer pos1 = Integer.parseInt(num_tmp[0]) - 1;
        Integer pos2 = Integer.parseInt(num_tmp[1]) - 1;
        char c = tmp[1].charAt(0);
        String str = tmp[2];

        if (pos1 >= str.length() || pos2 >= str.length()) {
            return 0;
        }

        int count = 0;
        if (str.charAt(pos1) == c) {
            count++;
        }
        if (str.charAt(pos2) == c) {
            count++;
        }
        return count == 1 ? 1 : 0;
    }

}
