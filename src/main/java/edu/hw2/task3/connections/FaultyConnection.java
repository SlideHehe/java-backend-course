package edu.hw2.task3.connections;

import edu.hw2.task3.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();
    private static boolean throwException = false;

    @Override
    public void execute(String command) {
        LOGGER.info("Trying to execute command: " + command);

        throwException = !throwException; // Для получения ошибки каждый 2 раз

        if (throwException) {
            throw new ConnectionException();
        }

        LOGGER.info("Command executed");
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Closing connection");
    }
}
