package edu.hw8.task1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeywordsServer {
    private static final int PORT = 8080;
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final int MAX_CONNECTIONS = 5;
    private static final int DEFAULT_TIMEOUT = 3000;

    private boolean isRunning = false;

    public void start() {
        try (
            ExecutorService service = Executors.newFixedThreadPool(MAX_CONNECTIONS);
            ServerSocket serverSocket = new ServerSocket(PORT)
        ) {
            isRunning = true;
            serverSocket.setSoTimeout(DEFAULT_TIMEOUT);
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    service.submit(() -> handleClient(clientSocket));
                } catch (SocketTimeoutException ignored) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        isRunning = false;
    }

    private static void handleClient(Socket clientSocket) {
        try (
            clientSocket;
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream()
        ) {
            byte[] request = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead = inputStream.read(request);

            String keyword = new String(request, 0, bytesRead);
            String response = InsultsLookupTable.getInsult(keyword);

            outputStream.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
