package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cristph
 * @date 2020/12/4 8:28 下午
 */
public class Day01 implements Solve {

    @Override
    public String solve(String[] args) {
        String fileName = args[0];
        Set<Long> set = new HashSet<>();

        Long a = null;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line = null;
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
}
