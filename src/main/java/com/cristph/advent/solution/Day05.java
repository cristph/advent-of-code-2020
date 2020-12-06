package com.cristph.advent.solution;

import com.cristph.advent.utils.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends AbstractDaySolve {

    @Override
    protected String solvePuzzle1(String[] args) {
        String fileName = args[0];
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return String.valueOf(br.lines().map(this::getSeat).map(p -> p.getLeft() * 8 + p.getRight()).max(Integer::compareTo).get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        String fileName = args[0];
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            List<Integer> list = br.lines().map(this::getSeat).map(p -> p.getLeft() * 8 + p.getRight()).sorted().collect(Collectors.toList());
            List<Integer> result = new ArrayList<>();
            for (int i = 1; i < list.size() - 1; i++) {
                if ((list.get(i - 1) == list.get(i) - 1) && (list.get(i + 1) == list.get(i) + 2)) {
                    result.add(list.get(i) + 1);
                }
            }
            if (result.size() != 1) {
                throw new RuntimeException("result size illegal");
            }
            return String.valueOf(result.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Pair<Integer, Integer> getSeat(String str) {
        if (str == null || str.length() != 10) {
            return null;
        }
        return new Pair<>(getSeatSeries(str.substring(0, 7)), getSeatSeries(str.substring(7, 10)));
    }

    public int getSeatSeries(String str) {
        int i = 0;
        int j = (1 << str.length()) - 1;
        int n = 0;
        while (i < j && n < str.length()) {
            Pair<Integer, Integer> pair = choose(i, j, str.charAt(n));
            i = pair.getLeft();
            j = pair.getRight();
            n++;
        }
        return i;
    }

    public Pair<Integer, Integer> choose(int i, int j, char c) {
        if (c == 'F' || c == 'L') {
            return new Pair<>(i, (i + j - 1) / 2);
        } else if (c == 'B' || c == 'R') {
            return new Pair<>((i + j + 1) / 2, j);
        } else {
            throw new RuntimeException("char c:" + c + " illegal");
        }
    }

}
