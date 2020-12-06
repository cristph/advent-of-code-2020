package com.cristph.advent;

import com.cristph.advent.utils.DataManager;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.time.LocalDate;

import static com.cristph.advent.constants.Constans.LINE_SEPARATOR;

/**
 * @author cristph
 * @date 2020/12/4 8:30 下午
 */
@Slf4j
public class Main {

    private static final String BASE_CLASS_NAME = "com.cristph.advent.solution.%s";
    private static final String BASE_CLASS_PREFIX = "Day";
    private static final String BASE_METHOD_NAME = "solve";
    private static final String BASE_RESOURCE_NAME = "src/main/resources/aoc_input/%s.txt";

    private static final LocalDate beginDate = LocalDate.of(2020, 12, 1);

    public static void main(String[] args) {
        int i = 1;
        int days = (int) (LocalDate.now().toEpochDay() - beginDate.toEpochDay()) + 1;
        days = days > 25 ? 25 : days;
        // getDataFromServer(days);
        while (i <= days) {
            String klassName = BASE_CLASS_PREFIX + (i < 10 ? "0" + i : i);
            try {
                Class klass = Class.forName(String.format(BASE_CLASS_NAME, klassName));
                Method method = klass.getMethod(BASE_METHOD_NAME, String[].class);
                Object result = method.invoke(klass.newInstance(),
                        (Object) new String[]{String.format(BASE_RESOURCE_NAME, klassName)});
                if (log.isInfoEnabled()) {
                    log.info("day" + i + " result:" + LINE_SEPARATOR + "{}" + LINE_SEPARATOR, result);
                }
            } catch (Exception e) {
                log.error("invoke error.", e);
            }
            i++;
        }
    }

    public static void getDataFromServer(int days) {

        DataManager.writeAllDaysToFile(2020, days);
    }
}
