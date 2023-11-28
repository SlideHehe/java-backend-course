package edu.hw8.task3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class PasswordDecryptor {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 4;

    public Map<String, String> decryptPasswords(Map<String, String> passwords) {
        Objects.requireNonNull(passwords);

        Map<String, String> decryptedPasswords = new HashMap<>();
        AtomicBoolean allPasswordsDecrypted = new AtomicBoolean(false);

        while (!allPasswordsDecrypted.get()) {
            String nextPassword = getNextPassword();
            processPasswords(passwords, decryptedPasswords, nextPassword, allPasswordsDecrypted);
        }
        return decryptedPasswords;
    }

    public Map<String, String> decryptPasswordWithMultithreading(Map<String, String> passwords, int numOfThreads) {
        Objects.requireNonNull(passwords);

        if (numOfThreads < 1) {
            throw new IllegalArgumentException("You can't create ThreadPool with less than 1 threads");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        ConcurrentMap<String, String> decryptedPasswords = new ConcurrentHashMap<>();
        AtomicBoolean allPasswordsDecrypted = new AtomicBoolean(false);

        Runnable task = () -> {
            while (!allPasswordsDecrypted.get()) {
                String nextPassword = getNextPassword();
                processPasswords(passwords, decryptedPasswords, nextPassword, allPasswordsDecrypted);
            }
        };

        for (int i = 0; i < numOfThreads; i++) {
            executorService.execute(task);
        }

        task.run();

        executorService.shutdown();
        return decryptedPasswords;
    }

    private void processPasswords(
        Map<String, String> passwords,
        Map<String, String> decryptedPasswords,
        String nextPassword,
        AtomicBoolean allPasswordsDecrypted
    ) {
        String encodedPassword = HashUtils.encodeToMd5(nextPassword);

        if (passwords.containsKey(encodedPassword)) {
            String username = passwords.get(encodedPassword);
            decryptedPasswords.put(username, nextPassword);

            if (decryptedPasswords.size() == passwords.size()) {
                allPasswordsDecrypted.set(true);
            }
        }
    }

    private String getNextPassword() {
        StringBuilder generatedPassword = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(ALPHABET.length());
            generatedPassword.append(ALPHABET.charAt(randomIndex));
        }
        return generatedPassword.toString();
    }
}
