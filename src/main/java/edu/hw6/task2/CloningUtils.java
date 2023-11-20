package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CloningUtils {
    private static final String COPY = " — копия";
    private static final String DOT = ".";

    private CloningUtils() {
    }

    public static Path cloneFile(Path path) {
        Objects.requireNonNull(path);

        if (!Files.exists(path)) {
            return null;
        }

        Path newPath = getNewPath(path);

        try {
            Files.copy(path, newPath);
        } catch (IOException ignored) {
            return null;
        }

        return newPath;
    }

    private static Path getNewPath(Path path) {
        String name = path.toString();

        if (!name.contains(DOT)) {
            return Path.of(name);
        }

        int dotIdx = name.lastIndexOf(DOT);

        String extension = name.substring(dotIdx + 1);
        String nameWithoutExtension = name.substring(0, dotIdx);

        return resolveNewName(nameWithoutExtension, extension);

    }

    private static Path resolveNewName(String nameWithoutExtension, String extension) {
        Path newName = Path.of(nameWithoutExtension + COPY + DOT + extension);

        int copyIdx = 2;

        while (Files.exists(newName)) {
            newName = Path.of(nameWithoutExtension + COPY + " (" + copyIdx + ")" + DOT + extension);
            copyIdx++;
        }

        return newName;
    }
}
