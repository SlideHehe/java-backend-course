package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import java.lang.reflect.Parameter;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

public class TypeGenerator {
    private static final Map<Class<?>, Function<Parameter, Object>> STRATEGIES = new HashMap<>();
    private static final String NOT_NULL_STRING = "IMAGINE THIS AS GENERATED VALUE";
    private static final Random RANDOM = new SecureRandom();

    private TypeGenerator() {
    }

    static {
        ValueParser<Integer> intValueParser = Integer::parseInt;
        RandomGenerator<Integer> intGenerator = RANDOM::nextInt;
        STRATEGIES.put(
            Integer.class,
            parameter -> parseLimitValue(parameter, intValueParser, intGenerator, Integer.MIN_VALUE, Integer.MAX_VALUE)
        );
        STRATEGIES.put(int.class, STRATEGIES.get(Integer.class));

        ValueParser<Long> longValueParser = Long::parseLong;
        RandomGenerator<Long> longGenerator = RANDOM::nextLong;
        STRATEGIES.put(
            Long.class,
            parameter -> parseLimitValue(parameter, longValueParser, longGenerator, Long.MIN_VALUE, Long.MAX_VALUE)
        );
        STRATEGIES.put(long.class, STRATEGIES.get(Long.class));

        ValueParser<Float> floatValueParser = Float::parseFloat;
        RandomGenerator<Float> floatGenerator = RANDOM::nextFloat;
        STRATEGIES.put(
            Float.class,
            parameter -> parseLimitValue(parameter, floatValueParser, floatGenerator, Float.MIN_VALUE, Float.MAX_VALUE)
        );
        STRATEGIES.put(float.class, STRATEGIES.get(Float.class));

        ValueParser<Double> doubleValueParser = Double::parseDouble;
        RandomGenerator<Double> doubleGenerator = RANDOM::nextDouble;
        STRATEGIES.put(
            Double.class,
            parameter -> parseLimitValue(
                parameter,
                doubleValueParser,
                doubleGenerator,
                -Double.MAX_VALUE,
                Double.MAX_VALUE
            )
        );
        STRATEGIES.put(double.class, STRATEGIES.get(Double.class));

        ValueParser<Character> charValueParser = v -> v.charAt(0);
        RandomGenerator<Character> charGenerator = (min, max) -> (char) RANDOM.nextInt(min, max);
        STRATEGIES.put(
            Character.class,
            parameter -> parseLimitValue(
                parameter,
                charValueParser,
                charGenerator,
                Character.MIN_VALUE,
                Character.MAX_VALUE
            )
        );
        STRATEGIES.put(char.class, STRATEGIES.get(Character.class));

        STRATEGIES.put(Boolean.class, parameter -> RANDOM.nextBoolean());

        STRATEGIES.put(String.class, parameter -> {
            String randomString = UUID.randomUUID().toString();

            if (Objects.nonNull(parameter.getAnnotation(NotNull.class)) && Objects.isNull(randomString)) {
                return NOT_NULL_STRING;
            }

            return randomString;
        });
    }

    private static <T> T parseLimitValue(
        Parameter parameter,
        ValueParser<T> parser,
        RandomGenerator<T> generator,
        T defaultMinValue,
        T defaultMaxValue
    ) {
        Min min = parameter.getAnnotation(Min.class);
        T parsedMinValue = (Objects.isNull(min)) ? defaultMinValue : parser.parse(min.value());

        Max max = parameter.getAnnotation(Max.class);
        T parsedMaxValue = (Objects.isNull(max)) ? defaultMaxValue : parser.parse(max.value());

        return generator.generate(parsedMinValue, parsedMaxValue);
    }

    public static Object generateRandomValue(Parameter param) {
        Function<Parameter, Object> generator = STRATEGIES.getOrDefault(param.getType(), p -> null);
        return generator.apply(param);
    }

    private interface ValueParser<T> {
        T parse(String s);
    }

    private interface RandomGenerator<T> {
        T generate(T min, T max);
    }
}
