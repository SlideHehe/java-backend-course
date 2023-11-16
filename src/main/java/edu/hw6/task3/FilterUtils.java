package edu.hw6.task3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.Objects;
import java.util.regex.Pattern;

public class FilterUtils {
    private FilterUtils() {
    }

    public static AbstractFilter regularFile = Files::isRegularFile;

    public static AbstractFilter readable = Files::isReadable;

    public static AbstractFilter largerThan(long size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException ignored) {
                return false;
            }
        };
    }

    public static AbstractFilter magicNumber(int... magicBytes) {
        return path -> {
            try (InputStream is = Files.newInputStream(path)) {

                for (int magicByte : magicBytes) {
                    if (magicByte != is.read()) {
                        return false;
                    }
                }

                return true;
            } catch (IOException ignored) {
                return false;
            }
        };
    }

    public static AbstractFilter globMatches(String glob) {
        Objects.requireNonNull(glob);

        return path -> {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
            return pathMatcher.matches(path);
        };
    }

    public static AbstractFilter regexContains(String regex) {
        Objects.requireNonNull(regex);

        return path -> {
            String fileName = path.getFileName().toString();
            Pattern pattern = Pattern.compile(regex);

            return pattern.matcher(fileName).find();
        };
    }
}
