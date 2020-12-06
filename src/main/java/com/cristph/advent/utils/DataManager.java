package com.cristph.advent.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class DataManager {

    private static String sessionCookie;

    public static void writeAllDaysToFile(int year, int days) {
        loadSession();
        for (int i = 1; i <= days; i++) {
            String filename = "Day" + pad(i) + ".txt";
            Path path = getBasePath(filename);
            getDataFromServer(i, year, path);
        }
    }

    @SneakyThrows
    private static void loadSession() {
        if (sessionCookie != null) {
            return;
        }
        Path path = Paths.get("src/main/resources", "session.txt");
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("No AOC session cookie found! Please create session.txt");
        }
        sessionCookie = new String(Files.readAllBytes(path));
    }

    public static String pad(int i) {
        return String.format("%02d", i);
    }

    private static void getDataFromServer(int day, int year, Path path) {
        String lines = null;
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("adventofcode.com")
                    .setPath("/" + year + "/day/" + day + "/input")
                    .build();

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("User-Agent", "cristph-AOC-Data-Bot/2.0.2.0 (+http://github.com/cristph/advent-of-code-2020)");
            httpGet.setHeader("Cookie", "session=" + sessionCookie);

            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                lines = EntityUtils.toString(entity, "utf-8");
                EntityUtils.consume(entity);
            }
            if (path != null) {
                writeFile(path, lines);
            }
        } catch (Exception e) {
            log.error("get data from server error. day:{}, year:{}, path:{}",
                    day, year, path, e);
        }
    }

    public static void writeFile(Path path, String lines) {
        if (lines == null || lines.length() == 0) {
            return;
        }
        Path parent = path.getParent();
        try {
            if (!Files.exists(parent)) {
                Files.createDirectory(parent);
            }
            Files.write(path, lines.trim().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (log.isInfoEnabled()) {
            log.info("finish load data to file : {}", path.toString());
        }
    }

    public static Path getBasePath(String filename) {
        return Paths.get("src/main/resources/aoc_input", filename);
    }

}