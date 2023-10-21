package edu.hw2.task3.managers;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}
