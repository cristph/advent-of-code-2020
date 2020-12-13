package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * @author cristph
 * @date 2020/12/11 2:43 下午
 */
public class Day09 extends AbstractDaySolve {

    @Override
    protected String solvePuzzle1(String[] args) {
        List<Long> numbers = getInput(args[0]);
        Queue<Long> queue = new ArrayBlockingQueue<>(25);
        for (int i = 0; i < 25; i++) {
            queue.offer(numbers.get(i));

        }
        for (int i = 25; i < numbers.size(); i++) {
            Long number = numbers.get(i);
            if (sumSatisfy(queue, number)) {
                queue.poll();
                queue.offer(number);
            } else {
                return String.valueOf(number);
            }
        }

        return null;
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        Long[] numbers = getInput(args[0]).toArray(new Long[0]);
        Long[] searchNumbers = search(numbers, 248131121L);
        Arrays.sort(searchNumbers);
        return String.valueOf(searchNumbers[0] + searchNumbers[searchNumbers.length - 1]);
    }

    private List<Long> getInput(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().map(Long::parseLong).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean sumSatisfy(Queue<Long> queue, Long sum) {
        Iterator iterator = queue.iterator();
        int i = 0;
        while (i < queue.size() - 1) {
            Long number = (Long)iterator.next();
            if (number * 2 != sum && queue.contains(sum - number)) {
                return true;
            }
            i++;
        }
        return false;
    }

    private Long[] search(Long[] numbers, long expectedSum) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                long sum = 0L;
                for (int k = i; k <= j; k++) {
                    sum += numbers[k];
                }
                if (sum == expectedSum) {
                    return Arrays.copyOfRange(numbers, i, j + 1);
                }
            }
        }
        return null;
    }

}
