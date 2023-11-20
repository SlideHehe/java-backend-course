package edu.hw6.task1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    private final HashMap<String, File> fileMap = new HashMap<>();
    private final String rootFolder;

    public DiskMap(String rootFolder) {
        Objects.requireNonNull(rootFolder);
        if (rootFolder.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.rootFolder = rootFolder;
        Path path = Path.of(rootFolder);

        try {
            Files.createDirectory(path);
        } catch (IOException ignored) {
        }

        File folder = path.toFile();

        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
            .forEach(file -> fileMap.put(file.getName(), file));
    }

    @Override
    public int size() {
        return fileMap.size();
    }

    @Override
    public boolean isEmpty() {
        return fileMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return fileMap.containsKey(key);
    }

    @Override
    public String get(Object key) {
        try (FileInputStream fileInputStream = new FileInputStream(fileMap.get(key))) {
            byte[] bytes = fileInputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return fileMap.keySet().stream()
            .anyMatch(key -> {
                String storedValue = get(key);
                return storedValue.equals(value);
            });
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        Objects.requireNonNull(key);

        String valueToReturn = null;

        if (fileMap.containsKey(key)) {
            valueToReturn = get(key);
        } else {
            try {
                File file = Files.createFile(Path.of(rootFolder, key)).toFile();
                fileMap.put(key, file);
            } catch (IOException ignored) {
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileMap.get(key))) {
            fileOutputStream.write(value.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }

        return valueToReturn;
    }

    @Override
    public String remove(Object key) {
        String valueToReturn = null;
        String stringKey = (String) key;

        if (fileMap.containsKey(stringKey)) {
            valueToReturn = get(key);
        }

        try {
            Files.delete(Path.of(rootFolder, stringKey));
        } catch (IOException ignored) {
        }

        return valueToReturn;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        fileMap.keySet().forEach(this::remove);
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return fileMap.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return fileMap.keySet().stream()
            .map(this::get)
            .collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return fileMap.keySet().stream()
            .map(k -> Map.entry(k, get(k)))
            .collect(Collectors.toSet());
    }
}
