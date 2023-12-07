package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class DirectoriesWithManyFiles extends RecursiveTask<List<Path>> {
    private final Path root;
    private final int numOfFiles;

    DirectoriesWithManyFiles(Path root, int numOfFiles) {
        this.root = root;
        this.numOfFiles = numOfFiles;
    }

    @Override
    protected List<Path> compute() {
        AtomicInteger counter = new AtomicInteger();
        List<Path> result = new ArrayList<>();
        List<DirectoriesWithManyFiles> subTasks = new ArrayList<>();

        try (Stream<Path> files = Files.list(root)) {
            files.forEach(path -> {
                    if (path.toFile().isDirectory()) {
                        DirectoriesWithManyFiles subtask = new DirectoriesWithManyFiles(path, numOfFiles);
                        subTasks.add(subtask);
                        subtask.fork();
                    } else {
                        counter.getAndIncrement();
                    }
                }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subTasks.forEach(task -> result.addAll(task.join()));

        if (counter.get() >= numOfFiles) {
            result.add(root);
        }

        return result;
    }
}
