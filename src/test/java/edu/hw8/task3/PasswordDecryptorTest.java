package edu.hw8.task3;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordDecryptorTest {

    @Test
    @DisplayName("Проверка расшифрофки паролей в однопоточном режиме")
    public void decryptPasswords() {
        // given
        PasswordDecryptor passwordDecryptor = new PasswordDecryptor();
        Map<String, String> passwords = new HashMap<>();
        passwords.put("e2fc714c4727ee9395f324cd2e7f331f", "p.p.petrov");
        passwords.put("65bb86549756830caa529e032f829eb2", "i.i.ivanov");

        // when
        Map<String, String> decryptedPasswords = passwordDecryptor.decryptPasswords(passwords);

        // then
        assertThat(decryptedPasswords.get("p.p.petrov")).isEqualTo("abcd");
        assertThat(decryptedPasswords.get("i.i.ivanov")).isEqualTo("q123");
    }

    @Test
    @DisplayName("Проверка расшифрофки паролей в многопоточном режиме")
    void multiThreadedDecryptPasswords() {
        // given
        PasswordDecryptor passwordDecryptor = new PasswordDecryptor();
        Map<String, String> passwords = new HashMap<>();
        passwords.put("e2fc714c4727ee9395f324cd2e7f331f", "p.p.petrov");
        passwords.put("65bb86549756830caa529e032f829eb2", "i.i.ivanov");

        // when
        Map<String, String> decryptedPasswords = passwordDecryptor.decryptPasswordWithMultithreading(passwords, 16);

        // then
        assertThat(decryptedPasswords.get("p.p.petrov")).isEqualTo("abcd");
        assertThat(decryptedPasswords.get("i.i.ivanov")).isEqualTo("q123");
    }

    @Test
    @DisplayName("Проверка передачи null вместо мапы в метод decryptPasswords")
    public void decryptPasswordsPassingNull() {
        // expect
        assertThatThrownBy(() -> new PasswordDecryptor().decryptPasswords(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка передачи null вместо мапы в метод decryptPasswordWithMultithreading")
    public void multiThreadedDecryptPasswordsPassingNull() {
        // expect
        assertThatThrownBy(() -> new PasswordDecryptor().decryptPasswordWithMultithreading(null, 2))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка передачи недопустимого количества потоков в метод decryptPasswordWithMultithreading")
    public void multiThreadedDecryptPasswordsInvalidNumOfThreads() {
        // expect
        assertThatThrownBy(() -> new PasswordDecryptor().decryptPasswordWithMultithreading(Map.of(), 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("You can't create ThreadPool with less than 1 threads");
    }
}
