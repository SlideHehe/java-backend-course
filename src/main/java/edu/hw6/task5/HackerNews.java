package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String HACKERS_NEWS_API = "https://hacker-news.firebaseio.com/v0";
    private static final Pattern TITLE_PATTERN = Pattern.compile("^.*\"title\":\"([^\"]*).*$");

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        String topStories = getRequestBody(HACKERS_NEWS_API + "/topstories.json");

        if (Objects.isNull(topStories) || topStories.equals("null")) {
            return new long[0];
        }

        return parseTopStoriesBody(topStories);
    }

    private static long[] parseTopStoriesBody(String topStories) {
        String[] topStoriesIds = topStories
            .substring(1, topStories.length() - 1)
            .split(",");

        long[] ids = new long[topStoriesIds.length];

        for (int i = 0; i < topStoriesIds.length; i++) {
            ids[i] = Long.parseLong(topStoriesIds[i]);
        }

        return ids;
    }

    public static String news(long id) {
        String newsJson = getRequestBody(HACKERS_NEWS_API + "/item/" + id + ".json");

        if (Objects.isNull(newsJson)) {
            return null;
        }

        Matcher matcher = TITLE_PATTERN.matcher(newsJson);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static String getRequestBody(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }
}

