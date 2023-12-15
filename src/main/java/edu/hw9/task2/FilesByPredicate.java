package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Stream;

class FilesByPredicate extends RecursiveTask<List<Path>> {
    private final Path root;
    private final Predicate<Path> predicate;

    FilesByPredicate(Path root, Predicate<Path> predicate) {
        this.root = root;
        this.predicate = predicate;
    }

    @Override
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        List<FilesByPredicate> subTasks = new ArrayList<>();

        try (Stream<Path> files = Files.list(root)) {
            files.forEach(path -> {
                if (path.toFile().isDirectory()) {
                    FilesByPredicate subTask = new FilesByPredicate(path, predicate);
                    subTask.fork();
                    subTasks.add(subTask);
                } else {
                    if (predicate.test(path)) {
                        result.add(path);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subTasks.forEach(task -> result.addAll(task.join()));

        return result;
    }
}
