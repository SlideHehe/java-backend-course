package edu.hw10.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class RandomObjectGenerator {
    private static final String CONSTRUCTOR_EXCEPTION = "There are no public constructors";
    private static final String FACTORY_METHOD_EXCEPTION = "There are no such factory method";
    private static final Random RANDOM = new SecureRandom();

    public <T> T nextObject(Class<T> tClass) {
        Objects.requireNonNull(tClass);

        Constructor<?>[] constructors = tClass.getConstructors();

        if (constructors.length == 0) {
            throw new IllegalArgumentException(CONSTRUCTOR_EXCEPTION);
        }

        Constructor<?> constructor = constructors[RANDOM.nextInt(constructors.length)];

        try {
            //noinspection unchecked
            return (T) constructor.newInstance(generateArgs(constructor));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(CONSTRUCTOR_EXCEPTION);
        }
    }

    public <T> T nextObject(Class<T> tClass, String factoryMethodName) {
        Objects.requireNonNull(tClass);
        Objects.requireNonNull(factoryMethodName);

        try {
            Method factoryMethod = Arrays.stream(tClass.getMethods())
                .filter(method -> method.getName().equals(factoryMethodName))
                .findFirst()
                .orElse(null);

            if (Objects.isNull(factoryMethod)) {
                throw new IllegalArgumentException(FACTORY_METHOD_EXCEPTION);
            }

            //noinspection unchecked
            return (T) factoryMethod.invoke(null, generateArgs(factoryMethod));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(FACTORY_METHOD_EXCEPTION);
        }
    }

    private Object[] generateArgs(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            args[i] = TypeGenerator.generateRandomValue(parameters[i]);
        }

        return args;
    }
}
