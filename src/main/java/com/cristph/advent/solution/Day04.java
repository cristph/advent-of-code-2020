package com.cristph.advent.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.cristph.advent.constants.Constans.LINE_SEPARATOR;

public class Day04 extends AbstractDaySolve {


    private static final HashSet<String> necessaryKeySet = new HashSet<>();

    static {
        necessaryKeySet.add("byr");
        necessaryKeySet.add("iyr");
        necessaryKeySet.add("eyr");
        necessaryKeySet.add("hgt");
        necessaryKeySet.add("hcl");
        necessaryKeySet.add("ecl");
        necessaryKeySet.add("pid");
        necessaryKeySet.add("cid");
    }

    @Override
    protected String solvePuzzle1(String[] args) {
        String fileName = args[0];
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            Set<String> keySet = new HashSet<>();
            while ((line = br.readLine()) != null) {
                if (isNewLine(line)) {
                    if (keySet.isEmpty() || (keySet.size() == 1 && keySet.contains("cid"))) {
                        count++;
                    }
                    keySet = new HashSet<>(necessaryKeySet);
                } else {
                    Map<String, String> kv = resolve(line);
                    if (kv != null) {
                        keySet.removeAll(kv.keySet());
                    }
                }
            }
            if (keySet.isEmpty() || (keySet.size() == 1 && keySet.contains("cid"))) {
                count++;
            }
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
            Set<String> keySet = new HashSet<>();
            boolean checkPass = true;
            while ((line = br.readLine()) != null) {
                if (isNewLine(line)) {
                    if (checkPass) {
                        if (keySet.isEmpty() || (keySet.size() == 1 && keySet.contains("cid"))) {
                            count++;
                        }
                    }
                    keySet = new HashSet<>(necessaryKeySet);
                    checkPass = true;
                } else {
                    Map<String, String> kv = resolve(line);
                    boolean satisfy = true;
                    for (Map.Entry<String, String> entry : kv.entrySet()) {
                        satisfy = satisfy && CheckHelper.check(entry.getKey(), entry.getValue());
                    }
                    if (kv != null && satisfy) {
                        keySet.removeAll(kv.keySet());
                    }
                    checkPass = checkPass && satisfy;
                }
            }

            if (checkPass) {
                if (keySet.isEmpty() || (keySet.size() == 1 && keySet.contains("cid"))) {
                    count++;
                }
            }
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

    private Map<String, String> resolve(String line) {
        if (line == null || line.length() == 0) {
            return null;
        }
        String str = line.trim();
        String[] tmp = str.split("\\s");
        if (tmp.length > 0) {
            return Arrays.stream(tmp)
                    .map(s -> s.split(":"))
                    .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
        }
        return null;
    }

    public static class CheckHelper {

        private static Map<String, Check> checkMap = new HashMap<>();

        static {
            checkMap.put("byr", new BYRCheck());
            checkMap.put("iyr", new IYRCheck());
            checkMap.put("eyr", new EYRCheck());
            checkMap.put("hgt", new HGTCheck());
            checkMap.put("hcl", new HCLCheck());
            checkMap.put("ecl", new ECLCheck());
            checkMap.put("pid", new PIDCheck());
            checkMap.put("cid", new CIDCheck());
        }


        public static boolean check(String k, String v) {
            Check check = checkMap.get(k);
            if (check == null) {
                return false;
            } else {
                return check.checkValid(v);
            }
        }

    }

    public interface Check {
        boolean checkValid(String str);
    }

    public static class BYRCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            return inRange(str, 1920, 2002);
        }
    }

    public static class IYRCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            return inRange(str, 2010, 2020);
        }
    }

    public static class EYRCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            return inRange(str, 2020, 2030);
        }
    }

    public static class HGTCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            if (isEmpty(str)) {
                return false;
            }
            if (str.endsWith("cm")) {
                return inRange(str.substring(0, str.length() - 2), 150, 193);
            } else if (str.endsWith("in")) {
                return inRange(str.substring(0, str.length() - 2), 59, 76);
            }
            return false;
        }
    }

    public static class HCLCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            if (isEmpty(str) || str.length() != 7) {
                return false;
            }
            if (str.startsWith("#")) {
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (!isDigit(c) && !isLittleAlphabet(c)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }

    public static class ECLCheck implements Check {

        List<String> validECL = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

        @Override
        public boolean checkValid(String str) {
            return inRange(str, validECL);
        }
    }

    public static class PIDCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            if (!isDigits(str) || str.length() != 9) {
                return false;
            }
            return true;
        }
    }

    public static class CIDCheck implements Check {

        @Override
        public boolean checkValid(String str) {
            return true;
        }
    }

    private static boolean isDigits(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDigit(char c) {
        if (c < 48 || c > 57) {
            return false;
        }
        return true;
    }

    private static boolean isLittleAlphabet(char c) {
        if (c < 97 || c > 102) {
            return false;
        }
        return true;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private static boolean inRange(String str, int low, int high) {
        if (isDigits(str)) {
            Integer n = Integer.parseInt(str);
            return low <= n && high >= n;
        }
        return false;
    }

    private static boolean inRange(String str, Collection<String> enums) {
        if (isEmpty(str)) {
            return false;
        }
        return enums.contains(str);
    }

}
