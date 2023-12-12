package edu.hw10.task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheProxy implements InvocationHandler {
    private static final String INVOKE_EXCEPTION = "Unable to invoke method ";
    private static final String IO_EXCEPTION = "Unable to create file ";
    private static final String CACHE_DIR = "cache";
    private final Object target;
    private final Map<Integer, Object> inMemoryCache;
    private final Path cacheDir;

    private CacheProxy(Object target, Map<Integer, Object> inMemoryCache, Path cacheDir) {
        this.target = target;
        this.inMemoryCache = inMemoryCache;
        this.cacheDir = cacheDir;
    }

    public static <T> T create(T target, Class<?> targetClass) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(targetClass);

        Map<Integer, Object> inMemoryCache = new HashMap<>();
        Path cacheDir = Path.of(CACHE_DIR);

        cacheDir.toFile().mkdir();
        cacheDir.toFile().deleteOnExit();

        //noinspection unchecked
        return (T) Proxy.newProxyInstance(
            targetClass.getClassLoader(),
            targetClass.getInterfaces(),
            new CacheProxy(target, inMemoryCache, cacheDir)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cache = method.getAnnotation(Cache.class);
        method.setAccessible(true);

        if (Objects.isNull(cache)) {
            return method.invoke(target, args);
        }

        Object result;

        if (cache.persist()) {
            result = loadCacheFromDisk(method, args);
        } else {
            result = loadCacheFromMap(method, args);
        }

        return result;
    }

    private Object loadCacheFromDisk(Method method, Object[] args) {
        String hash = String.valueOf(Arrays.hashCode(args));
        Path hashedFile = cacheDir.resolve(Path.of(hash));

        if (Files.exists(hashedFile)) {
            return readFromFile(hashedFile);
        } else {
            return writeToFile(hashedFile, method, args);
        }
    }

    private Object readFromFile(Path path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(IO_EXCEPTION + path);
        }
    }

    private Object writeToFile(Path path, Method method, Object[] args) {
        try {
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(IO_EXCEPTION + path);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            Object result = method.invoke(target, args);
            oos.writeObject(result);
            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(INVOKE_EXCEPTION + method);
        } catch (IOException e) {
            throw new RuntimeException(IO_EXCEPTION + path);
        }
    }

    private Object loadCacheFromMap(Method method, Object[] args) {
        int hash = Arrays.hashCode(args);

        if (inMemoryCache.containsKey(hash)) {
            return inMemoryCache.get(hash);
        }

        try {
            Object result = method.invoke(target, args);
            inMemoryCache.put(hash, result);
            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(INVOKE_EXCEPTION + method);
        }
    }
}
