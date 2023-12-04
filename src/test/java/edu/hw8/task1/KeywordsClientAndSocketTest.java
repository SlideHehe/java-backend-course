package edu.hw8.task1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class KeywordsClientAndSocketTest {
    @Test
    @DisplayName("Проверка получения фразы по ключевому слову")
    void getQuoteTest() throws InterruptedException {
        // given
        KeywordsServer keywordsServer = new KeywordsServer();
        Thread thread = new Thread(keywordsServer::start);
        thread.start();

        // when
        Thread.sleep(100);
        KeywordsClient keywordsClient = new KeywordsClient();
        String actual = keywordsClient.getResponse("оскорбления");

        // then
        assertThat(actual)
            .isEqualTo("Если твои противники перешли на личные оскорбления, будь уверен — твоя победа не за горами");
        keywordsServer.stop();
        thread.join();
    }

    @Test
    @DisplayName("Проверка получения неизвестной фразы")
    void getUnknownQuote() throws InterruptedException {
        // given
        KeywordsServer keywordsServer = new KeywordsServer();
        Thread thread = new Thread(keywordsServer::start);
        thread.start();

        // when
        Thread.sleep(100);
        KeywordsClient keywordsClient = new KeywordsClient();
        String actual = keywordsClient.getResponse("абиба");

        // then
        assertThat(actual)
            .isEqualTo("Неизвестное слово. Пожалуйста введите другое");
        keywordsServer.stop();
        thread.join();
    }

    @Test
    @DisplayName("Проверка подключения нескольких клиентов")
    void multipleClientTest() throws InterruptedException {
        // given
        KeywordsServer keywordsServer = new KeywordsServer();
        Thread thread = new Thread(keywordsServer::start);
        thread.start();

        // expect
        Thread.sleep(100);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                String actual = new KeywordsClient().getResponse("личности");
                assertThat(actual).isEqualTo("Не переходи на личности там, где их нет");
            });
        }
        Thread.sleep(1000);
        service.shutdown();
        keywordsServer.stop();
        thread.join();
    }

    @Test
    @DisplayName("Передача null вместо ключевого слова")
    void clientNullKeyword() {
        // expect
        assertThatThrownBy(() -> new KeywordsClient().getResponse(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Передача пустого ключевого слова")
    void clientEmptyKeyword() {
        // expect
        assertThatThrownBy(() -> new KeywordsClient().getResponse(" "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("You can't pass an empty keyword");
    }
}
