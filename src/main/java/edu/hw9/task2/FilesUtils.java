package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;

public class FilesUtils {
    private static final String SIZE_EXCEPTION = "File size can't be less than 0";

    private FilesUtils() {
    }

    public static List<Path> getDirectoriesWithManyFiles(Path root, int numOfFiles) {
        Objects.requireNonNull(root);

        if (numOfFiles < 1) {
            throw new IllegalArgumentException("Illegal number of files (Can't be less than 1)");
        }

        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            return commonPool.invoke(new DirectoriesWithManyFiles(root, numOfFiles));
        }
    }

    public static List<Path> getFilesLargerThan(Path root, long size) {
        Objects.requireNonNull(root);

        if (size < 0) {
            throw new IllegalArgumentException(SIZE_EXCEPTION);
        }

        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            return commonPool.invoke(new FilesByPredicate(root, path -> {
                try {
                    return Files.size(path) > size;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    public static List<Path> getFilesLessThan(Path root, long size) {
        Objects.requireNonNull(root);

        if (size < 0) {
            throw new IllegalArgumentException(SIZE_EXCEPTION);
        }

        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            return commonPool.invoke(new FilesByPredicate(root, path -> {
                try {
                    return Files.size(path) < size;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    public static List<Path> getFilesByExtension(Path root, String extension) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(extension);

        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            return commonPool.invoke(new FilesByPredicate(root, path -> {
                String fileName = path.getFileName().toString();
                return fileName.substring(fileName.lastIndexOf('.') + 1).equals(extension);
            }));
        }
    }
}
