package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketScan {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PORT_RANGE = 49151;
    private static final String BLANK_STRING = "";
    private static final String FORMATTED_HEADER = String.format(
        "%8s\t" + "%5s\t" + "%9s\t%s",
        "Protocol", "Port", "Status", "Service"
    );

    private SocketScan() {
    }

    public static void logAvailableSocketsInfo() {
        List<SocketInfo> socketInfos = getSocketsInfo();

        LOGGER.info(FORMATTED_HEADER);

        for (SocketInfo socketInfo : socketInfos) {
            if (socketInfo.available()) {
                LOGGER.info(socketInfo);
            }
        }
    }

    public static void logTakenSocketsInfo() {
        List<SocketInfo> socketInfos = getSocketsInfo();

        LOGGER.info(FORMATTED_HEADER);

        for (SocketInfo socketInfo : socketInfos) {
            if (!socketInfo.available()) {
                LOGGER.info(socketInfo);
            }
        }
    }

    public static void logAllSocketsInfo() {
        List<SocketInfo> socketInfos = getSocketsInfo();

        LOGGER.info(FORMATTED_HEADER);

        for (SocketInfo socketInfo : socketInfos) {
            LOGGER.info(socketInfo);
        }
    }

    private static List<SocketInfo> getSocketsInfo() {
        List<SocketInfo> socketInfos = new ArrayList<>();

        for (int port = 0; port <= PORT_RANGE; port++) {
            socketInfos.add(scanTCPSocket(port));
            socketInfos.add(scanUDPSocket(port));
        }

        return socketInfos;
    }

    private static SocketInfo scanTCPSocket(int port) {
        boolean available;

        try (ServerSocket ignored = new ServerSocket(port)) {
            available = true;
        } catch (IOException e) {
            available = false;
        }

        return new SocketInfo(
            Protocol.TCP, port,
            available,
            ServicesLookupTable.SERVICES.getOrDefault(port, BLANK_STRING)
        );
    }

    private static SocketInfo scanUDPSocket(int port) {
        boolean available;

        try (DatagramSocket ignored = new DatagramSocket(port)) {
            available = true;
        } catch (SocketException e) {
            available = false;
        }

        return new SocketInfo(
            Protocol.UDP, port,
            available,
            ServicesLookupTable.SERVICES.getOrDefault(port, BLANK_STRING)
        );
    }
}
