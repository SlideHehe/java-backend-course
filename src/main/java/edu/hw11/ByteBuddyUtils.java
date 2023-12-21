package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyUtils {
    public static final String FIBONACCI_CALCULATOR = "FibonacciCalculator";

    private ByteBuddyUtils() {
    }

    // Task 1
    public static Object getHelloByteBuddyInstance() {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(ByteBuddyUtils.class.getClassLoader())
            .getLoaded();

        try {
            return dynamicType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException("Unable to create HelloByteBuddy instance");
        }
    }

    // Task 2
    public static void changeArithmeticUtilsBehaviour() {
        ByteBuddyAgent.install();

        new ByteBuddy()
            .redefine(SumReloading.class)
            .name(ArithmeticUtils.class.getName())
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }

    // Task 3
    public static Class<?> getFibCalculatorClass() {
        return new ByteBuddy()
            .subclass(Object.class)
            .name(FIBONACCI_CALCULATOR)
            .defineMethod("fib", long.class, Modifier.PUBLIC).withParameters(int.class)
            .intercept(new Implementation.Simple(new FibonacciByteCodeGenerator()))
            .make()
            .load(ByteBuddyUtils.class.getClassLoader())
            .getLoaded();
    }
}
