package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cristph
 * @date 2020/12/4 8:28 下午
 */
public class Day01 implements Solve {

    @Override
    public String solve(String[] args) {
        PuzzleResult puzzleResult = PuzzleResultBuilder.newBuilder()
                .append("puzzle1", solvePuzzle1(args))
                .append("puzzle2", solvePuzzle2(args))
                .build();
        return puzzleResult.toString();
    }

    private String solvePuzzle1(String[] args) {
        String fileName = args[0];
        Set<Long> set = new HashSet<>();

        Long a = null;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Long num = Long.parseLong(line);
                if (set.contains(new Long(2020 - num))) {
                    a = num;
                    break;
                } else {
                    set.add(num);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a == null ? null : String.valueOf(a * (2020L - a));
    }

    List<List<Long>> puzzle2Result = new ArrayList<>();

    /**
     * space O(n), time O(1)
     *
     * @param args
     * @return
     */
    private String solvePuzzle2(String[] args) {
        String fileName = args[0];
        List<Long> list = null;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            list = br.lines().map(Long::parseLong).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sum(list, new ArrayList<>(3), 2020L, list.size() - 1, 3);

        return puzzle2Result.size() == 0 ? null : String.valueOf(puzzle2Result.get(0).stream().reduce(1L, (x, y) -> x * y));
    }

    /**
     * select k number from list, sum of which is expectedSum
     *
     * @param list
     * @param selected    store the selected numbers
     * @param expectedSum
     * @param n           position of selected number
     * @param k
     */
    private void sum(List<Long> list, List<Long> selected, long expectedSum, int n, int k) {
        if (expectedSum < 0 || n < 0) {
            return;
        }

        if (expectedSum == 0 && k == 0) {
            puzzle2Result.add(Arrays.asList(Arrays.copyOf(selected.toArray(new Long[0]), selected.size())));
        }

        // 放第n个数
        selected.add(list.get(n));
        sum(list, selected, expectedSum - list.get(n), n - 1, k - 1);

        // 不放第n个数
        selected.remove(list.get(n));
        sum(list, selected, expectedSum, n - 1, k);
    }
}
