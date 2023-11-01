package edu.hw2.task3.managers;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.FaultyConnection;
import edu.hw2.task3.connections.StableConnection;

public class DefaultConnectionManager implements ConnectionManager {
    private static boolean returnFaulty = false;

    @Override
    public Connection getConnection() {
        returnFaulty = !returnFaulty; // Для FaultyConnection каждый 2 раз

        if (returnFaulty) {
            return new FaultyConnection();
        }

        return new StableConnection();
    }
}
