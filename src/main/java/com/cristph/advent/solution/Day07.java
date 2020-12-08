package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cristph
 * @date 2020/12/7 3:07 下午
 */
@Slf4j
public class Day07 extends AbstractDaySolve {

    Pattern pattern = Pattern.compile("(.+) bag.*");
    Pattern pattern2 = Pattern.compile("(\\d+) (.+) bag.*");

    @Override
    protected String solvePuzzle1(String[] args) {
        ColorBox colorBox = initColorMatrix(args);
        Map<String, Integer> map = colorBox.getColorMap();
        int column = map.get("shiny gold");
        int count = 0;
        int[][] colorMatrix = colorBox.getColorMatrix();
        for (int i = 0; i < colorMatrix.length; i++) {
            for (int j = 0; j < colorMatrix.length; j++) {
                if (colorMatrix[i][j] >= 0) {
                    for (int h = 0; h < colorMatrix.length; h++) {
                        if (h != i && colorMatrix[h][i] >= 0) {
                            colorMatrix[h][j] = (colorMatrix[h][j] > 0 ? colorMatrix[h][j] : 0) + colorMatrix[h][i]
                                * colorMatrix[i][j];
                        }
                        if (h != j && colorMatrix[j][h] >= 0) {
                            colorMatrix[i][h] = (colorMatrix[i][h] > 0 ? colorMatrix[i][h] : 0) + colorMatrix[i][j]
                                * colorMatrix[j][h];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < colorMatrix.length; i++) {
            if (i != column) {
                count += colorMatrix[i][column] > 0 ? 1 : 0;
            }
        }
        return String.valueOf(count);
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        ColorBox colorBox = initColorMatrix(args);
        Map<String, Integer> map = colorBox.getColorMap();
        int row = map.get("shiny gold");
        int[][] colorMatrix = colorBox.getColorMatrix();
        solveColorMatrix(colorMatrix, row, 1);
        return String.valueOf(count);
    }

    private static AtomicInteger count = new AtomicInteger(0);

    private void solveColorMatrix(int[][] colorMatrix, int row, int ct) {
        int k = ct;
        for (int i = 0; i < colorMatrix.length; i++) {
            if (i != row) {
                if (colorMatrix[row][i] > 0) {
                    ct = colorMatrix[row][i] * k;
                    count.getAndAdd(ct);
                    solveColorMatrix(colorMatrix, i, ct);
                }
            }
        }
    }

    private ColorBox initColorMatrix(String[] args) {
        String fileName = args[0];
        List<String> lines = null;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            lines = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] colorMatrix = new int[lines.size()][lines.size()];
        Map<String, Integer> map = new HashMap<>(lines.size());

        for (int i = 0; i < lines.size(); i++) {
            String[] tmp = lines.get(i).split(" bags contain");
            map.put(tmp[0], i);
        }

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                colorMatrix[i][j] = -1;
            }
        }

        for (String line : lines) {

            String[] tmp = line.split(" contain ");
            Matcher matcher = pattern.matcher(tmp[0]);
            matcher.matches();
            String color = matcher.group(1);

            if (line.contains("contain no other bags")) {
                for (int i = 0; i < map.size(); i++) {
                    colorMatrix[map.get(color)][i] = 0;
                }
                continue;
            }

            String[] t = tmp[1].split(", ");

            for (int j = 0; j < t.length; j++) {
                Matcher matcher2 = pattern2.matcher(t[j]);
                matcher2.matches();
                colorMatrix[map.get(color)][map.get(matcher2.group(2))] = Integer.parseInt(matcher2.group(1));
            }
        }
        return new ColorBox(colorMatrix, map);
    }

    @Data
    @AllArgsConstructor
    private class ColorBox {

        /**
         * colorMatrix[i][j] = n means box[i] contains n box[j]
         */
        private int[][] colorMatrix;

        /**
         * key:color
         * value:matrix index
         */
        private Map<String, Integer> colorMap;

    }

}
