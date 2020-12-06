package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cristph.advent.constants.Constans.LINE_SEPARATOR;

public class Day06 extends AbstractDaySolve {

    @Override
    protected String solvePuzzle1(String[] args) {
        String fileName = args[0];
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            Set<Character> set = new HashSet<>();
            while ((line = br.readLine()) != null) {
                if (isNewLine(line)) {
                    count += set.size();
                    set.clear();
                } else {
                    set.addAll(resolve(line));
                }
            }
            count += set.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(count);
    }

    @Override
    protected String solvePuzzle2(String[] args) {
        String fileName = args[0];
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            List<Set<Character>> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (isNewLine(line)) {
                    Set<Character> set = list.get(0);
                    for (int i = 1; i < list.size(); i++) {
                        set.retainAll(list.get(i));
                    }
                    count += set.size();
                    list.clear();
                } else {
                    list.add(new HashSet<>(resolve(line)));
                }
            }
            Set<Character> set = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                set.retainAll(list.get(i));
            }
            count += set.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(count);
    }


    private boolean isNewLine(String line) {
        if (line == null) {
            return false;
        }
        String str = line.trim();
        return LINE_SEPARATOR.equals(line) || str.length() == 0;
    }

    private List<Character> resolve(String str) {
        return str.chars().mapToObj(i -> (char) i).distinct().collect(Collectors.toList());
    }
}
