package edu.hw6.task6;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("MagicNumber")
public class ServicesLookupTable {
    public static final Map<Integer, String> SERVICES = new HashMap<>();

    static {
        SERVICES.put(123, "NTP");
        SERVICES.put(135, "EPMAP");
        SERVICES.put(137, "NetBIOS Name Service");
        SERVICES.put(138, "NetBIOS Datagram Service");
        SERVICES.put(139, "NetBIOS Session Service");
        SERVICES.put(445, "MICROSOFT-DS");
        SERVICES.put(500, "ISAKMP");
    }

    private ServicesLookupTable() {
    }
}
