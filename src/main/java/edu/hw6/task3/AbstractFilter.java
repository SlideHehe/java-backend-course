package edu.hw6.task3;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Objects;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(AbstractFilter other) {
        Objects.requireNonNull(other);

        return path -> this.accept(path) && other.accept(path);
    }
}
