package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cristph
 * @date 2020/12/4 10:05 下午
 */
public class Day02 implements Solve {

    List<List<Long>> result = new ArrayList<>();

    @Override
    public String solve(String[] args) {
        String fileName = args[0];
        List<Long> list = null;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            list = br.lines().map(Long::parseLong).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sum(list, new ArrayList<>(3), 2020L, list.size() - 1, 3);

        return result.size() == 0 ? null : String.valueOf(result.get(0).stream().reduce(1L, (x, y) -> x * y));
    }

    public void sum(List<Long> list, List<Long> selected, long expectedSum, int n, int k) {
        if (expectedSum < 0 || n < 0) {
            return;
        }

        if (expectedSum == 0 && k == 0) {
            result.add(Arrays.asList(Arrays.copyOf(selected.toArray(new Long[0]), selected.size())));
        }

        // 放第n个数
        selected.add(list.get(n));
        sum(list, selected, expectedSum - list.get(n), n - 1, k - 1);

        // 不放第n个数
        selected.remove(list.get(n));
        sum(list, selected, expectedSum, n - 1, k);
    }
}
