package edu.hw2.task3;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.managers.ConnectionManager;
import java.util.Objects;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        if (Objects.isNull(manager) || maxAttempts <= 0) {
            throw new IllegalArgumentException();
        }

        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    public void installPackage(String packageName) {
        validateString(packageName);

        String installCommand = "apt install " + packageName + " -y";
        tryExecute(installCommand);
    }

    public void makeDir(String dirName) {
        validateString(dirName);

        String makeDirCommand = "mkdir " + dirName;
        tryExecute(makeDirCommand);
    }

    private void tryExecute(String command) {
        int attempts = 0;

        while (true) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (Exception e) {
                if (attempts == maxAttempts - 1) {
                    throw new ConnectionException(e);
                }

                attempts++;
            }
        }
    }

    private void validateString(String command) {
        if (Objects.isNull(command) || command.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
