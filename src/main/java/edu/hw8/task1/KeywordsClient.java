package edu.hw8.task1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

public class KeywordsClient {
    private static final int SERVER_PORT = 8080;
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final String SERVER_HOST = "127.0.0.1";

    public String getResponse(String keyword) {
        validateString(keyword);

        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()
        ) {
            outputStream.write(keyword.getBytes());

            byte[] response = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead = inputStream.read(response);
            return new String(response, 0, bytesRead);
        } catch (IOException ignored) {
            return null;
        }
    }

    private static void validateString(String keyword) {
        Objects.requireNonNull(keyword);
        if (keyword.isBlank()) {
            throw new IllegalArgumentException("You can't pass an empty keyword");
        }
    }
}
