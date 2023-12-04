package edu.hw8.task1;

import java.util.HashMap;
import java.util.Map;

public class InsultsLookupTable {
    private static final Map<String, String> INSULTS = new HashMap<>();

    static {
        INSULTS.put("личности", "Не переходи на личности там, где их нет");
        INSULTS.put(
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверен — твоя победа не за горами"
        );
        INSULTS.put(
            "глупый",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
        );
        INSULTS.put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
    }

    private InsultsLookupTable() {
    }

    public static String getInsult(String keyword) {
        return INSULTS.getOrDefault(keyword, "Неизвестное слово. Пожалуйста введите другое");
    }
}
