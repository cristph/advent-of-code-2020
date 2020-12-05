package com.cristph.advent;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cristph
 * @date 2020/12/4 8:30 下午
 */
@Slf4j
public class Main {

    private static final String BASE_CLASS_NAME = "com.cristph.advent.solution.%s";
    private static final String BASE_CLASS_PREFIX = "Day";
    private static final String BASE_METHOD_NAME = "solve";
    private static final String BASE_RESOURCE_NAME = "src/main/resources/%s.txt";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String[] args) {
        int i = 1;
        while (i <= 2) {
            String klassName = BASE_CLASS_PREFIX + (i < 10 ? "0" + i : i);
            try {
                Class klass = Class.forName(String.format(BASE_CLASS_NAME, klassName));
                Method method = klass.getMethod(BASE_METHOD_NAME, String[].class);
                Object result = method.invoke(klass.newInstance(),
                        (Object) new String[]{String.format(BASE_RESOURCE_NAME, klassName)});
                if (log.isInfoEnabled()) {
                    log.info("day result:" + LINE_SEPARATOR + "{}" + LINE_SEPARATOR, result);
                }
            } catch (Exception e) {
                log.error("invoke error.", e);
            }
            i++;
        }
    }
}
